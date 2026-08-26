"""Shared lexer helper: strip comments and string literals from Kotlin source.

Every checker needs to look at CODE only -- a brace inside a string or a
capitalised word inside a comment must never be treated as syntax.
"""
import re


def code_only(text):
    text = re.sub(r'"""(?:.|\n)*?"""', '""', text)          # raw strings
    text = re.sub(r'(?<!\\)"(?:[^"\\\n]|\\.)*"', '""', text)  # normal strings
    text = re.sub(r"'(?:[^'\\\n]|\\.)'", "' '", text)        # char literals
    text = re.sub(r'/\*(?:.|\n)*?\*/', '', text)             # block comments
    text = re.sub(r'//[^\n]*', '', text)                     # line comments
    return text
