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
        "total": "35",
        "ok": "35",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "20268",
        "ok": "20268",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "8979",
        "ok": "8979",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "5372",
        "ok": "5372",
        "ko": "-"
    },
    "percentiles1": {
        "total": "9504",
        "ok": "9503",
        "ko": "-"
    },
    "percentiles2": {
        "total": "12934",
        "ok": "12910",
        "ko": "-"
    },
    "percentiles3": {
        "total": "17691",
        "ok": "17691",
        "ko": "-"
    },
    "percentiles4": {
        "total": "18999",
        "ok": "18999",
        "ko": "-"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 897,
        "percentage": 9
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 271,
        "percentage": 3
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 8832,
        "percentage": 88
    },
    "group4": {
        "name": "failed",
        "count": 0,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "400",
        "ok": "400",
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
        "total": "10000",
        "ok": "10000",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "35",
        "ok": "35",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "20268",
        "ok": "20268",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "8979",
        "ok": "8979",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "5372",
        "ok": "5372",
        "ko": "-"
    },
    "percentiles1": {
        "total": "9505",
        "ok": "9504",
        "ko": "-"
    },
    "percentiles2": {
        "total": "12914",
        "ok": "12910",
        "ko": "-"
    },
    "percentiles3": {
        "total": "17691",
        "ok": "17691",
        "ko": "-"
    },
    "percentiles4": {
        "total": "18999",
        "ok": "18999",
        "ko": "-"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 897,
        "percentage": 9
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 271,
        "percentage": 3
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 8832,
        "percentage": 88
    },
    "group4": {
        "name": "failed",
        "count": 0,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "400",
        "ok": "400",
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
