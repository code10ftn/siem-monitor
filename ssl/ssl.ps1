# Generate server (CA) key and certificate.
openssl genrsa -out server.key 2048
openssl req -x509 -new -nodes -key server.key -out server.pem -sha256 -days 3650 -passout env:CODE10_SIEM_CERT_SERVER_KEY_PASS -subj "/C=RS/ST=./L=Novi Sad/O=code10ftn/OU=SIEM/CN=localhost"

# Generate agent key and certificate signed by our CA.
openssl genrsa -out agent.key 2048
openssl req -new -key agent.key -out agent.csr -sha256 -days 1095 -passout env:CODE10_SIEM_CERT_CLIENT_KEY_PASS -subj "/C=RS/ST=./L=Novi Sad/O=code10ftn/OU=SIEM/CN=Agent"
openssl x509 -req -in agent.csr -CA server.pem -CAkey server.key -CAcreateserial -out agent.pem

# Generate client key and certificate signed by our CA.
openssl genrsa -out client.key 2048
openssl req -new -key client.key -out client.csr -sha256 -days 1095 -passout env:CODE10_SIEM_CERT_CLIENT_KEY_PASS -subj "/C=RS/ST=./L=Novi Sad/O=code10ftn/OU=SIEM/CN=Client"
openssl x509 -req -in client.csr -CA server.pem -CAkey server.key -CAserial server.srl -out client.pem

# Export server's key and certificate to a keystore.
openssl pkcs12 -export -out serverKeyStore.p12 -inkey server.key -in server.pem -passout env:CODE10_SIEM_CERT_SERVER_KEY_PASS

# Export agent's key and certificate to a keystore.
openssl pkcs12 -export -out agentKeyStore.p12 -inkey agent.key -in agent.pem -passout env:CODE10_SIEM_CERT_CLIENT_KEY_PASS

# Export server, agent and client certificates to a truststore (required for mutual authentication).
keytool -import -storetype PKCS12 -noprompt -trustcacerts -alias serverCert -file server.pem -keystore serverTrustStore.p12 -storepass $env:CODE10_SIEM_CERT_SERVER_KEY_PASS
keytool -import -storetype PKCS12 -noprompt -trustcacerts -alias agentCert -file agent.pem -keystore serverTrustStore.p12 -storepass $env:CODE10_SIEM_CERT_SERVER_KEY_PASS
keytool -import -storetype PKCS12 -noprompt -trustcacerts -alias clientCert -file client.pem -keystore serverTrustStore.p12 -storepass $env:CODE10_SIEM_CERT_SERVER_KEY_PASS

# Delete unnecessary files. When new certificate is needed, server's key and certificate can be exported from the keystore:
# openssl pkcs12 -in serverKeyStore.p12 -nocerts -out server.key -passin env:CODE10_SIEM_CERT_SERVER_KEY_PASS -passout env:CODE10_SIEM_CERT_SERVER_KEY_PASS
# openssl pkcs12 -in serverKeyStore.p12 -clcerts -nokeys -out server.pem -passin env:CODE10_SIEM_CERT_SERVER_KEY_PASS -passout env:CODE10_SIEM_CERT_SERVER_KEY_PASS
Remove-Item .\agent.csr
Remove-Item .\agent.key
Remove-Item .\agent.pem
Remove-Item .\client.csr
Remove-Item .\server.key
Remove-Item .\server.srl

# Distribute files.
Move-Item -Path .\serverKeyStore.p12 -Destination ..\SiemCore\src\main\resources -Force
Move-Item -Path .\serverTrustStore.p12 -Destination ..\SiemCore\src\main\resources -Force
Move-Item -Path .\agentKeyStore.p12 -Destination ..\SiemAgent\resources -Force
Move-Item -Path .\server.pem -Destination ..\SiemAgent\resources -Force
Move-Item -Path .\client.key -Destination ..\SiemClient\src\assets\ssl -Force
Move-Item -Path .\client.pem -Destination ..\SiemClient\src\assets\ssl -Force