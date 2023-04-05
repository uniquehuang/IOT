function StringBuilder() {
    this.strings = new Array("");
    this.strings.length = 0;
}

// Appends the given value to the end of this instance.
StringBuilder.prototype.append = function (value) {
    if (value) {
        this.strings.push(value);
    }
}
// Clears the string buffer
StringBuilder.prototype.clear = function () {
    this.strings.length = 0;
}
// Converts this instance to a String.
StringBuilder.prototype.toString = function () {
    return this.strings.join("");
}

StringBuilder.prototype.size = function () {
    return this.strings.length;
}