### [function-whitespace](https://github.com/sleekbyte/tailor/issues/89)

Every function and method declaration should have _one_ blank line before and after itself. An exception to this rule are functions that are declared at the start of a file (only need one blank line after their declaration) or at the end of a file (only need one blank line before their declaration). Comments immediately before a function declaration (no blank lines between them and the function) are considered to be part of the declaration.

*Preferred*

```
func function1() {
  var text = 1
  var text = 2
}

function1()

// a comment
func function2() {
  // something goes here
}

struct SomeStruct {

  func function3() {
    // something goes here
  }

  func function4() {
    // something else goes here
  };

}

func function5() {
  // something goes here
}
```

*Not Preferred*

```
func function1() {
  var text = 1
  var text = 2
}
function1()
// a comment
func function2() {
  // something goes here
}

struct SomeStruct {
  func function3() {
    // something goes here
  }

  func function4() {
    // something else goes here
  };
}
func function5() {
  // something goes here
}
```
