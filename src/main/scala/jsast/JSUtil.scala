package jsast

object JSUtil {
	def quoteRegexp(s:String):String	= s flatMap quoteRegexpChar
		
	def quoteRegexpChar(char:Char):String	= char match {
		case	'(' | ')' | 
				'{' | '}' | 
				'[' | ']' | 
				'|' | '+' |
				'*' | '?' | 
				'\\' | '/'	=> "\\" + char.toString
		case '\r'			=> "\\r"
		case '\n'			=> "\\n"
		case '\t'			=> "\\t"
		case '\f'			=> "\\f"
		case 11				=> "\\v"
		case x if x < 32	=> "\\u%04x" format x.toInt
		case x				=> x.toString
	}
	
	def escapeStringChar(char:Char, doubleQuote:Boolean=true, singleQuote:Boolean=true):String	= char match {
		case '"'	if doubleQuote	=> "\\\""
		case '\''	if singleQuote	=> "\\\""
		case '\\'					=>	"\\\\"
		case '\b'					=> "\\b"
		case '\f'					=> "\\f"
		case '\n'					=> "\\n"
		case '\r'					=> "\\r"
		case '\t'					=> "\\t"
		case c		if c < 32		=> "\\u%04x" format c.toInt
		case c 						=> c.toString
	}
}
