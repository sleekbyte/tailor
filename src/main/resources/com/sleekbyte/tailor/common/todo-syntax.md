### [todo-syntax](https://github.com/sleekbyte/tailor/issues/68)
TODO comments should be defined separately using non-nested single line comments. They should adhere to the `<TODO: description>` or `<TODO(developer-name): description>` syntax. Empty TODO comments will be flagged.

*Preferred*

```
// TODO: <insert mandatory todo comment>
// TODO(dev-name): <insert mandatory todo comment>
```

*Not Preferred*

```
// TODO:

/// TODO: Documentation comments should not have TODOs

//// TODO: Nested comments should not have TODOs

// //TODO: Nested comments should not have TODOs

// TODO: Nested comments should not have TODOs // some comment

//// TODO: Nested comments should not have TODOs
```
