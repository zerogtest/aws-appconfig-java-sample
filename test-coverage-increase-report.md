# Test Coverage Increase Report

## Baseline Coverage

- **Total source files**: 12
- **Source files with matching test files**: 3 (Math.java, Movie.java, MoviesController.java)
- **Baseline coverage**: 25.0% (3/12)

## Final Coverage

- **Total source files**: 12
- **Source files with matching test files**: 10
- **Final coverage**: 83.3% (10/12)
- **Total test files generated**: 7

## Generated Test Files

| # | Generated Test File | Source File Covered |
|---|---|---|
| 1 | `src/test/java/com/amazonaws/samples/appconfig/cache/ConfigurationCacheItemTest.java` | `src/main/java/com/amazonaws/samples/appconfig/cache/ConfigurationCacheItem.java` |
| 2 | `src/test/java/com/amazonaws/samples/appconfig/cache/ConfigurationCacheTest.java` | `src/main/java/com/amazonaws/samples/appconfig/cache/ConfigurationCache.java` |
| 3 | `src/test/java/com/amazonaws/samples/appconfig/model/ConfigurationKeyTest.java` | `src/main/java/com/amazonaws/samples/appconfig/model/ConfigurationKey.java` |
| 4 | `src/test/java/com/amazonaws/samples/appconfig/utils/AppConfigUtilityTest.java` | `src/main/java/com/amazonaws/samples/appconfig/utils/AppConfigUtility.java` |
| 5 | `src/test/java/com/amazonaws/samples/appconfig/utils/HTMLBuilderTest.java` | `src/main/java/com/amazonaws/samples/appconfig/utils/HTMLBuilder.java` |
| 6 | `src/test/java/com/amazonaws/samples/appconfig/utils/EncoderTest.java` | `src/main/java/com/amazonaws/samples/appconfig/utils/Encoder.java` |
| 7 | `src/test/java/com/amazonaws/samples/appconfig/utils/SecurityTest.java` | `src/main/java/com/amazonaws/samples/appconfig/utils/Security.java` |

## Uncovered Source Files

| # | Source File | Reason |
|---|---|---|
| 1 | `src/main/java/com/amazonaws/samples/appconfig/movies/MoviesApplication.java` | Spring Boot main class - minimal testable logic |
| 2 | `movie-service-utils/src/main/java/com/amazonaws/samples/appconfig/utils/MovieUtils.java` | Submodule source file |

## Coverage Breakdown

| Source File | Test File | Status |
|---|---|---|
| ConfigurationCache.java | ConfigurationCacheTest.java | Covered |
| ConfigurationCacheItem.java | ConfigurationCacheItemTest.java | Covered |
| ConfigurationKey.java | ConfigurationKeyTest.java | Covered |
| Movie.java | MovieTest.java | Covered (pre-existing) |
| MoviesApplication.java | - | Uncovered |
| MoviesController.java | MoviesControllerTest.java | Covered (pre-existing) |
| AppConfigUtility.java | AppConfigUtilityTest.java | Covered |
| Encoder.java | EncoderTest.java | Covered |
| HTMLBuilder.java | HTMLBuilderTest.java | Covered |
| Math.java | MathTest.java | Covered (pre-existing) |
| Security.java | SecurityTest.java | Covered |
| MovieUtils.java (submodule) | - | Uncovered |

**Covered: 10 | Uncovered: 2 | Total: 12 | Coverage: 83.3%**
