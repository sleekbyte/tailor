### [forced-type-cast](https://github.com/sleekbyte/tailor/issues/114)
Avoid using the forced form of the type cast operator (`as!`) because Swift is not able to determine at compile time if the type conversion will succeed.  In the event of an unsuccessful conversion, a runtime error will be triggered. The conditional form of the type cast operator (`as?`) is safer and should be used when possible.

*Preferred*

```
if let movie = item as? Movie {
    print("Movie: '\(movie.name)', dir. \(movie.director)")
}
```

*Not Preferred*

```
let movie = item as! Movie
print("Movie: '\(movie.name)', dir. \(movie.director)")
```
