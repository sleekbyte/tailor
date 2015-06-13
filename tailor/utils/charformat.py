def is_upper_camel_case(word):
    return word and word.isalnum() and ('_' not in word) and word[0].isupper()


def is_lower_camel_case(word):
    return word and word.isalnum() and ('_' not in word) and word[0].islower()
