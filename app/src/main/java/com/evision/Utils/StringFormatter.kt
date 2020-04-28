package com.evision.Utils

class StringFormatter {
    companion object {
        public fun convertUTF8ToString(s: String): String? {
            var out: String? = null
            try {
                out = String(s.toByteArray(charset("ISO-8859-1")))
            } catch (e: java.io.UnsupportedEncodingException) {
                return null
            }

            return out
        }

        // convert internal Java String format to UTF-8
        public fun convertStringToUTF8(s: String): String? {
            var out: String? = null
            try {
                out = String(s.toByteArray(charset("UTF-8")))
            } catch (e: java.io.UnsupportedEncodingException) {
                return null
            }

            return out
        }
    }
}