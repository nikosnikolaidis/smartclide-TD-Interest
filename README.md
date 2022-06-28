<!--
   Copyright (C) 2021-2022 University of Macedonia
   
   This program and the accompanying materials are made
   available under the terms of the Eclipse Public License 2.0
   which is available at https://www.eclipse.org/legal/epl-2.0/
   
   SPDX-License-Identifier: EPL-2.0
-->
# TD Interest API

The backend service that start new analysis for the calculation of the TD Interest and accesses the results. This component is a REST API implemented in Spring Boot.

<p>
Default port: 8080
</p>

## (GET) Cumulative Interest

### Mandatory Query Parameters

1. **url**: String

### Optional Query Parameters

1. **sha**: String

### Example Request

```url
/api/cumulativeInterest?url=https://github.com/apache/commons-io&sha=b10969081c7e9a8b8529167ccfb10e6be14e9a94
```

### Response

```json
[
   {
      "sha":"b10969081c7e9a8b8529167ccfb10e6be14e9a94",
      "revisionCount":3056,
      "interestEu":1635.52,
      "interestHours":41.5
   }
]
```

## (GET) Interest Change +- (relative to last revision)

### Mandatory Query Parameters

1. **url**: String
2. **sha**: String

### Example Request

```url
/api/interestChange?url=https://github.com/apache/commons-io&sha=b10969081c7e9a8b8529167ccfb10e6be14e9a94
```

### Response

```json
[
   {
      "sha":"b10969081c7e9a8b8529167ccfb10e6be14e9a94",
      "revisionCount":3056,
      "changeEu":0.00,
      "changeHours":0.0,
      "changePercentage":0E-20
   }
]
```

## (GET) Normalized Interest

### Mandatory Query Parameters

1. **url**: String

### Optional Query Parameters

1. **sha**: String

### Example Request

```url
/api/normalizedInterest?url=https://github.com/apache/commons-io&sha=b10969081c7e9a8b8529167ccfb10e6be14e9a94
```

### Response
```json
[
   {
      "sha":"b10969081c7e9a8b8529167ccfb10e6be14e9a94",
      "revisionCount":3056,
      "normalizedInterestEu":0.05203538306606205815,
      "normalizedInterestHours":0.00131935555441333917
   }
]
```

## (GET) High Interest Files

### Mandatory Query Parameters

1. **url**: String
2. **sha**: String

### Optional Query Parameters

1. **limit**: Integer

### Example Request

```url
/api/highInterestFiles?url=https://github.com/apache/commons-io&sha=b10969081c7e9a8b8529167ccfb10e6be14e9a94&limit=2
```

### Response

```json
[
   {
      "sha":"b10969081c7e9a8b8529167ccfb10e6be14e9a94",
      "revisionCount":3056,
      "filePath":"src/test/java/org/apache/commons/io/DirectoryWalkerTestCase.java",
      "interestEu":377.65,
      "interestHours":9.58,
      "interestPercentageOfProject":0.23090240149663948119
   },
   {
      "sha":"b10969081c7e9a8b8529167ccfb10e6be14e9a94",
      "revisionCount":3056,
      "filePath":"src/test/java/org/apache/commons/io/input/TailerTest.java",
      "interestEu":326.36,
      "interestHours":8.27,
      "interestPercentageOfProject":0.19954465546832622660
   }
]
```

## (GET) Analyzed Commits

### Mandatory Query Parameters

1. **url**: String

### Optional Query Parameters

1. **limit**: Integer

### Example Request

```url
/api/analyzedCommits?url=https://github.com/apache/commons-io&limit=2
```

### Response

```json
[
   {
      "sha":"5111e23b01eb9e8e44361438395658d815aa0d3b",
      "revisionCount":3185
   },
   {
      "sha":"2e8281f0965b45853065422633841291ed996c48",
      "revisionCount":3184
   }
]
```

## (GET) Reusability Metrics Per Commit (Project Level)

### Mandatory Query Parameters

1. **url**: String

### Optional Query Parameters

1. **limit**: Integer

### Example Request

```url
/api/reusabilityMetrics?url=https://github.com/apache/commons-io&limit=2
```

### Response

```json
[
   {
      "sha":"9423d7dc927d7d44980b08b59b2a127f8f26ae99",
      "revisionCount":1,
      "cbo":0.0,
      "dit":0.0,
      "wmc":0.0,
      "rfc":0.0,
      "lcom":0.0,
      "nocc":0.0
   },
   {
      "sha":"9eb351eb37d45d68027efedd7a961249dd573f9f",
      "revisionCount":2,
      "cbo":0.0,
      "dit":0.0,
      "wmc":0.0,
      "rfc":0.0,
      "lcom":0.0,
      "nocc":0.0
   }
]
```

## (GET) Reusability Metrics By Commit (File Level)

### Mandatory Query Parameters

1. **url**: String
2. **sha**: String

### Optional Query Parameters

1. limit: Integer

### Example Request

```url
/api/reusabilityMetricsByCommit?url=https://github.com/apache/commons-io&limit=2
```

### Response

```json
[
   {
      "sha":"b10969081c7e9a8b8529167ccfb10e6be14e9a94",
      "revisionCount":3056,
      "filePath":"src/main/java/org/apache/commons/io/ByteOrderMark.java",
      "cbo":0,
      "dit":1,
      "wmc":7,
      "rfc":24,
      "lcom":0,
      "nocc":0
   },
   {
      "sha":"b10969081c7e9a8b8529167ccfb10e6be14e9a94",
      "revisionCount":3056,
      "filePath":"src/main/java/org/apache/commons/io/ByteOrderParser.java",
      "cbo":0,
      "dit":1,
      "wmc":1,
      "rfc":4,
      "lcom":0,
      "nocc":0
   },
   {
      "sha":"b10969081c7e9a8b8529167ccfb10e6be14e9a94",
      "revisionCount":3056,
      "filePath":"src/main/java/org/apache/commons/io/Charsets.java",
      "cbo":0,
      "dit":1,
      "wmc":3,
      "rfc":7,
      "lcom":3,
      "nocc":0
   },
   {
      "sha":"b10969081c7e9a8b8529167ccfb10e6be14e9a94",
      "revisionCount":3056,
      "filePath":"src/main/java/org/apache/commons/io/comparator/AbstractFileComparator.java",
      "cbo":0,
      "dit":1,
      "wmc":3,
      "rfc":9,
      "lcom":3,
      "nocc":0
   },
   {
      "sha":"b10969081c7e9a8b8529167ccfb10e6be14e9a94",
      "revisionCount":3056,
      "filePath":"src/main/java/org/apache/commons/io/comparator/CompositeFileComparator.java",
      "cbo":1,
      "dit":1,
      "wmc":2,
      "rfc":10,
      "lcom":0,
      "nocc":0
   }
]
```

## (GET) Reusability Metrics By Commit And File

### Mandatory Query Parameters

1. **url**: String
2. **sha**: String
3. **filePath**: String

### Optional Query Parameters

1. **limit**: Integer

### Example Request

```url
/api/reusabilityMetricsByCommitAndFile?url=https://github.com/apache/commons-io&sha=b10969081c7e9a8b8529167ccfb10e6be14e9a94&filePath=src/main/java/org/apache/commons/io/comparator/AbstractFileComparator.java
```

### Response

```json
[
   {
      "sha":"b10969081c7e9a8b8529167ccfb10e6be14e9a94",
      "revisionCount":3056,
      "filePath":"src/main/java/org/apache/commons/io/comparator/AbstractFileComparator.java",
      "cbo":0,
      "dit":1,
      "wmc":3,
      "rfc":9,
      "lcom":3,
      "nocc":0
   }
]
```

## (POST) Start Analysis

### Mandatory Header

```json
"Content-Type":  "application/json"
```

### Example Request

```url
/api/startInterestAnalysis
```

### Example Bodies

```json
{
    "url": "https://github.com/dimizisis/racegame"
}
```
,
```json
{
    "url": "https://github.com/dimizisis/racegame",
    "token": "xxx"
}
```

### Response (If successful)
```json
{
    "id": 61,
    "url": "https://github.com/dimizisis/racegame",
    "owner": "dimizisis",
    "repo": "racegame"
}
```
