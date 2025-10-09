# 🧬 Mutation Testing Guide for Dinosaur Exploder

## What is Mutation Testing?

Mutation testing is a technique that evaluates the quality of your test suite by introducing small changes (mutations) to your source code and checking if your tests catch these changes. If a mutation causes a test to fail, it's "killed" ✅. If tests still pass, the mutation "survived" ❌, indicating a potential gap in your test coverage.

## 🚀 Quick Start

### Run Mutation Testing

```powershell
mvn org.pitest:pitest-maven:mutationCoverage
```

or simply:

```powershell
mvn pitest:mutationCoverage
```

### View Results

After running, open the HTML report:
```
target/pit-reports/YYYYMMDDHHMI/index.html
```

## 📊 Understanding the Report

The mutation testing report shows:

- **Line Coverage**: Percentage of lines executed by tests
- **Mutation Coverage**: Percentage of mutations killed by tests
- **Test Strength**: How effective your tests are at catching bugs

### Mutation Score Interpretation

- **90-100%**: Excellent test quality 🌟
- **70-89%**: Good test coverage ✅
- **50-69%**: Needs improvement ⚠️
- **Below 50%**: Poor test coverage ❌

## 🔧 Configuration Details

### Target Classes

The mutation testing is configured to test:
- `com.dinosaur.dinosaurexploder.model.*` - Game models
- `com.dinosaur.dinosaurexploder.utils.*` - Utility classes
- `com.dinosaur.dinosaurexploder.components.*` - Game components
- `com.dinosaur.dinosaurexploder.controller.*` - Controllers

### Excluded Classes

These are excluded from mutation testing:
- `DinosaurApp` - Main application class
- `DinosaurWebApp` - Web application class
- View classes - UI components (hard to test effectively)

### Mutation Operators (STRONGER)

The `STRONGER` mutator group includes:
- **Conditionals Boundary** - Changes `<` to `<=`, etc.
- **Increments** - Changes `++` to `--`
- **Invert Negatives** - Changes `-x` to `+x`
- **Math Operations** - Changes `+` to `-`, `*` to `/`, etc.
- **Negate Conditionals** - Changes `==` to `!=`, etc.
- **Return Values** - Changes return values
- **Remove Conditionals** - Removes if/else conditions

## 📋 Common Commands

### Run mutation testing only
```powershell
mvn pitest:mutationCoverage
```

### Run tests first, then mutation testing
```powershell
mvn clean test pitest:mutationCoverage
```

### Skip tests and run mutation testing (not recommended)
```powershell
mvn pitest:mutationCoverage -DskipTests
```

### Run with custom thread count
```powershell
mvn pitest:mutationCoverage -Dthreads=8
```

### Run with verbose output
```powershell
mvn pitest:mutationCoverage -X
```

## 🎯 Best Practices

1. **Run regularly**: Run mutation tests before committing major changes
2. **Incremental analysis**: PITest tracks history for faster subsequent runs
3. **Focus on logic**: Mutation testing is most valuable for business logic
4. **Don't aim for 100%**: Some mutations are acceptable to survive
5. **Review survivors**: Analyze surviving mutations to improve tests

## 🔍 Interpreting Surviving Mutations

### Common Reasons Mutations Survive:

1. **Equivalent Mutations**: Changes that don't affect behavior
   - Example: Changing `i++` to `++i` in some contexts
   
2. **Missing Edge Cases**: Your tests don't cover all scenarios
   - **Action**: Add test cases for edge conditions
   
3. **Weak Assertions**: Tests run but don't check the right things
   - **Action**: Strengthen assertions in existing tests
   
4. **Dead Code**: Code that never executes
   - **Action**: Remove or refactor unused code

## 📈 Improving Mutation Score

### Strategy 1: Add Boundary Tests
```java
@Test
void testScoreAtBoundary() {
    // Test score at exactly 0, max value, etc.
}
```

### Strategy 2: Test Edge Cases
```java
@Test
void testEmptyList() {
    // Test behavior with empty collections
}

@Test
void testNullValues() {
    // Test null handling
}
```

### Strategy 3: Strengthen Assertions
```java
// ❌ Weak
@Test
void testAddScore() {
    addScore(10);
    // No assertion!
}

// ✅ Strong
@Test
void testAddScore() {
    int initial = getScore();
    addScore(10);
    assertEquals(initial + 10, getScore());
}
```

## 🛠️ Advanced Configuration

### Set Mutation Thresholds

Uncomment these lines in `pom.xml` to enforce minimum scores:

```xml
<mutationThreshold>60</mutationThreshold>
<coverageThreshold>70</coverageThreshold>
```

This will fail the build if scores are below thresholds.

### Use Different Mutator Groups

Change the mutator in `pom.xml`:

```xml
<mutators>
    <mutator>ALL</mutator>           <!-- All mutations -->
    <mutator>DEFAULTS</mutator>      <!-- Default set -->
    <mutator>STRONGER</mutator>      <!-- More aggressive (current) -->
    <mutator>OLD_DEFAULTS</mutator>  <!-- Legacy compatibility -->
</mutators>
```

### Custom Mutators

```xml
<mutators>
    <mutator>CONDITIONALS_BOUNDARY</mutator>
    <mutator>INCREMENTS</mutator>
    <mutator>MATH</mutator>
    <mutator>NEGATE_CONDITIONALS</mutator>
</mutators>
```

## 📁 Output Files

PITest generates several files:

- `index.html` - Main report with summary
- `[ClassName].java.html` - Per-class detailed reports
- `mutations.xml` - Machine-readable results
- `linecoverage.xml` - Line coverage data

## 🐛 Troubleshooting

### OutOfMemoryError
Increase heap size in `pom.xml`:
```xml
<jvmArg>-Xmx2048m</jvmArg>
```

### Tests timing out
Increase timeout in `pom.xml`:
```xml
<timeoutConstant>6000</timeoutConstant>
```

### Too slow
- Reduce thread count if system is overloaded
- Use incremental analysis: `<withHistory>true</withHistory>`
- Exclude slow tests or classes

## 📚 Further Reading

- [PITest Official Documentation](https://pitest.org/)
- [PITest Quick Start](https://pitest.org/quickstart/)
- [Mutation Testing Best Practices](https://pitest.org/quickstart/mutators/)

---

**Happy Mutation Testing! 🧬🦖**
