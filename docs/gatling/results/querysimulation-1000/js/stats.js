var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "1000",
        "ok": "1000",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "34",
        "ok": "34",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "3217",
        "ok": "3217",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "731",
        "ok": "731",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "808",
        "ok": "808",
        "ko": "-"
    },
    "percentiles1": {
        "total": "493",
        "ok": "493",
        "ko": "-"
    },
    "percentiles2": {
        "total": "1021",
        "ok": "1021",
        "ko": "-"
    },
    "percentiles3": {
        "total": "3134",
        "ok": "3134",
        "ko": "-"
    },
    "percentiles4": {
        "total": "3211",
        "ok": "3211",
        "ko": "-"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 689,
        "percentage": 69
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 116,
        "percentage": 12
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 195,
        "percentage": 20
    },
    "group4": {
        "name": "failed",
        "count": 0,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "200",
        "ok": "200",
        "ko": "-"
    }
},
contents: {
"req_query-56620": {
        type: "REQUEST",
        name: "QUERY",
path: "QUERY",
pathFormatted: "req_query-56620",
stats: {
    "name": "QUERY",
    "numberOfRequests": {
        "total": "1000",
        "ok": "1000",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "34",
        "ok": "34",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "3217",
        "ok": "3217",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "731",
        "ok": "731",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "808",
        "ok": "808",
        "ko": "-"
    },
    "percentiles1": {
        "total": "493",
        "ok": "493",
        "ko": "-"
    },
    "percentiles2": {
        "total": "1021",
        "ok": "1021",
        "ko": "-"
    },
    "percentiles3": {
        "total": "3134",
        "ok": "3134",
        "ko": "-"
    },
    "percentiles4": {
        "total": "3211",
        "ok": "3211",
        "ko": "-"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 689,
        "percentage": 69
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 116,
        "percentage": 12
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 195,
        "percentage": 20
    },
    "group4": {
        "name": "failed",
        "count": 0,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "200",
        "ok": "200",
        "ko": "-"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
