# Unit Test Coverage Report (Static Analysis)

## Summary

| Metric | Value |
|---|---|
| **Coverage Percentage** | **25.0%** |
| Total Source Files | 12 |
| Source Files With Tests | 3 |
| Source Files Without Tests | 9 |

The project is **below** the 80% unit test coverage threshold.

## Covered Files

| Source File | Test File |
|---|---|
| `src/main/java/com/amazonaws/samples/appconfig/movies/Movie.java` | `src/test/java/com/amazonaws/samples/appconfig/movies/MovieTest.java` |
| `src/main/java/com/amazonaws/samples/appconfig/movies/MoviesController.java` | `src/test/java/com/amazonaws/samples/appconfig/movies/MoviesControllerTest.java` |
| `src/main/java/com/amazonaws/samples/appconfig/utils/Math.java` | `src/test/java/com/amazonaws/samples/appconfig/movies/MathTest.java` |

### Note on Unmatched Test Files

- `MockTest.java` — Contains generic Mockito usage tests that do not correspond to any specific source file.

## Files Missing Tests

### `src/main/java/com/amazonaws/samples/appconfig/cache/`

- `ConfigurationCache.java`
- `ConfigurationCacheItem.java`

### `src/main/java/com/amazonaws/samples/appconfig/model/`

- `ConfigurationKey.java`

### `src/main/java/com/amazonaws/samples/appconfig/movies/`

- `MoviesApplication.java`

### `src/main/java/com/amazonaws/samples/appconfig/utils/`

- `AppConfigUtility.java`
- `Encoder.java`
- `HTMLBuilder.java`
- `Security.java`

### `movie-service-utils/src/main/java/com/amazonaws/samples/appconfig/utils/`

- `MovieUtils.java`

## Recommendation

The project has a unit test file coverage of **25.0%**, which is significantly below the **80% threshold**. Unit tests should be added for the 9 uncovered source files listed above. Priority should be given to utility and cache classes (`AppConfigUtility.java`, `ConfigurationCache.java`, `Encoder.java`, `Security.java`) which are likely shared across the application and would benefit most from test coverage.
