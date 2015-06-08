def is_upper_camel_case(word):
    return word and ('_' not in word) and word[0].isupper()
