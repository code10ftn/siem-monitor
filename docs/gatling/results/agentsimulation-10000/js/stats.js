var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "10000",
        "ok": "10000",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "11",
        "ok": "11",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "3844",
        "ok": "3844",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "574",
        "ok": "574",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "654",
        "ok": "654",
        "ko": "-"
    },
    "percentiles1": {
        "total": "413",
        "ok": "413",
        "ko": "-"
    },
    "percentiles2": {
        "total": "626",
        "ok": "626",
        "ko": "-"
    },
    "percentiles3": {
        "total": "2180",
        "ok": "2180",
        "ko": "-"
    },
    "percentiles4": {
        "total": "3116",
        "ok": "3116",
        "ko": "-"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 8127,
        "percentage": 81
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 1083,
        "percentage": 11
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 790,
        "percentage": 8
    },
    "group4": {
        "name": "failed",
        "count": 0,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "1000",
        "ok": "1000",
        "ko": "-"
    }
},
contents: {
"req_agent-355e2": {
        type: "REQUEST",
        name: "AGENT",
path: "AGENT",
pathFormatted: "req_agent-355e2",
stats: {
    "name": "AGENT",
    "numberOfRequests": {
        "total": "10000",
        "ok": "10000",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "11",
        "ok": "11",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "3844",
        "ok": "3844",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "574",
        "ok": "574",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "654",
        "ok": "654",
        "ko": "-"
    },
    "percentiles1": {
        "total": "413",
        "ok": "413",
        "ko": "-"
    },
    "percentiles2": {
        "total": "626",
        "ok": "626",
        "ko": "-"
    },
    "percentiles3": {
        "total": "2182",
        "ok": "2185",
        "ko": "-"
    },
    "percentiles4": {
        "total": "3116",
        "ok": "3116",
        "ko": "-"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 8127,
        "percentage": 81
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 1083,
        "percentage": 11
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 790,
        "percentage": 8
    },
    "group4": {
        "name": "failed",
        "count": 0,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "1000",
        "ok": "1000",
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
