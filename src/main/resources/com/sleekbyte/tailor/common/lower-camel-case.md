### [lower-camel-case](https://github.com/sleekbyte/tailor/issues/9)
`method` and `var` names should follow *lowerCamelCase* naming convention: first letter of the entire word is lowercase, but subsequent first letters are uppercase.
#### Method and selector names
*Preferred*

```
func someMethod() {
	// method definition goes here
}
```

*Not Preferred*

```
func some-method() {
	// method definition goes here
}
```

#### Variable names
*Preferred*

```
var someVariable = someValue
```

*Not Preferred*

```
var Some_Var1able = someValue
```
