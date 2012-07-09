package jsast

object JSUnparser {
	def unparse(expr:JSExpr):String	= expr match {
		// case JSUnparsed(code)							=> code
		
		// literal values
		
		case JSUndefined								=> "undefined"
		case JSNaN										=> "NaN"
		case JSNull										=> "null"
		case JSString(value)							=> value map { escapeStringChar(_,true,false) } mkString("\"","","\"")
		case JSRegexp(pattern, options)					=> "/" + pattern + "/" + options
		case JSBoolean(value:Boolean)					=> value.toString
		case JSNumber(value:Number)						=> value.toString
		case JSArray(items)								=> items map unparse mkString ("[", ", ", "]")
		case JSObject(items)							=> items map { case (k,v) => unparse(JSString(k)) + ": " + unparse(v) } mkString ("{", ", ", "}")
		
		// arithmetic binary
		
		case JSAdd(left, right)		=> unparse(left) + "+" + unparse(right)
		case JSSub(left, right)		=> unparse(left) + "-" + unparse(right)
		case JSMul(left, right)		=> unparse(left) + "*" + unparse(right)
		case JSDiv(left, right)		=> unparse(left) + "/" + unparse(right)
		case JSMod(left, right)		=> unparse(left) + "%" + unparse(right)

		// arithmetic unary
		
		case JSNeg(sub)				=> "-" + unparse(sub)
		case JSPos(sub)				=> "+" + unparse(sub)
		
		// comparison
		
		case JSEq(left, right)		=> unparse(left) + "=="		+ unparse(right)
		case JSNe(left, right)		=> unparse(left) + "!="		+ unparse(right)
		case JSEqEq(left, right)	=> unparse(left) + "==="	+ unparse(right)
		case JSNeNe(left, right)	=> unparse(left) + "!=="	+ unparse(right)
		case JSGt(left, right)		=> unparse(left) + ">"		+ unparse(right)
		case JSLt(left, right)		=> unparse(left) + "<"		+ unparse(right)
		case JSGe(left, right)		=> unparse(left) + ">="		+ unparse(right)
		case JSLe(left, right)		=> unparse(left) + "<="		+ unparse(right)
		
		// logical
		
		case JSNot(sub)				=> "!" + unparse(sub)
		case JSAnd(left, right)		=> unparse(left) + "&&"	+ unparse(right)
		case JSOr(left, right)		=> unparse(left) + "||"	+ unparse(right)
		
		// bitwise
		
		case JSBitNot(sub)					=> "~" + unparse(sub)
		case JSBitAnd(left, right)			=> unparse(left) + "&"	+ unparse(right)
		case JSBitOr(left, right)			=> unparse(left) + "|"	+ unparse(right)
		case JSBitXor(left, right)			=> unparse(left) + "^"	+ unparse(right)
		case JSBitLeft(left, right)			=> unparse(left) + "<<"		+ unparse(right)
		case JSBitRight(left, right)		=> unparse(left) + ">>"		+ unparse(right)
		case JSBitRightFill(left, right)	=> unparse(left) + ">>>"	+ unparse(right)
		
		// special
		
		case JSIn(left, right)				=> unparse(left) + " in "	+ unparse(right)
		
		case JSTernary(condition, trueCase, falseCase)	
											=> unparse(condition) + "?"	+ unparse(trueCase) + ":" + unparse(falseCase)
		
		case JSParens(sub)					=> "(" + unparse(sub) + ")"
		
		case JSComma(left, right)			=> unparse(left) + ","	+ unparse(right)
		
		case JSDeref(lvalue, name)			=> unparse(lvalue) + "."	+ name
		case JSAccess(lvalue, key)			=> unparse(lvalue) + "["	+ unparse(key) + "]"

		case JSNew(call)					=> "new " + unparse(call)
		case JSCall(function, arguments)	=> function + (arguments map unparse mkString ("(", ", ", ")"))
	}
	
	private def escapeStringChar(char:Char, doubleQuote:Boolean=true, singleQuote:Boolean=true):String	= char match {
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
	
	// private def indent(s:String):String	= 
	// 		s replaceAll ("(?m)^", "\t")
}
