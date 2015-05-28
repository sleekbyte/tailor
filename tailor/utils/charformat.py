def isUpperCamelCase(word):
    return word and (not '_' in word) and word[0].isupper()
