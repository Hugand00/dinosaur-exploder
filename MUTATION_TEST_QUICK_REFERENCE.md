# 🧬 Mutation Testing - Quick Reference Card

## 🚀 Run Mutation Tests

```powershell
mvn pitest:mutationCoverage
```

## 📊 View Report

Open after running:
```
target/pit-reports/[TIMESTAMP]/index.html
```

## 🎯 What Gets Tested

✅ **Included:**
- `model.*` - Game models
- `utils.*` - Utilities
- `components.*` - Game components  
- `controller.*` - Controllers

❌ **Excluded:**
- `DinosaurApp` - Main class
- `DinosaurWebApp` - Web app
- View classes

## 📈 Score Meaning

| Score | Quality |
|-------|---------|
| 90-100% | ⭐ Excellent |
| 70-89% | ✅ Good |
| 50-69% | ⚠️ Needs Work |
| <50% | ❌ Poor |

## 🔧 Common Commands

```powershell
# Full test + mutation
mvn clean test pitest:mutationCoverage

# More threads (faster)
mvn pitest:mutationCoverage -Dthreads=8

# Verbose output
mvn pitest:mutationCoverage -X
```

## 🐛 Troubleshooting

**Too Slow?**
- Reduce threads in `pom.xml`
- Uses history tracking (faster after first run)

**Out of Memory?**
- Increase `-Xmx` value in `pom.xml`

**Tests Timing Out?**
- Increase `timeoutConstant` in `pom.xml`

## 📚 More Info

See `MUTATION_TESTING.md` for complete guide.

---

**Quick Tip:** Run mutation tests before major commits! 🦖
