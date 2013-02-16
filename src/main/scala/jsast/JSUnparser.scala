package jsast

object JSUnparser {
	def unparse(expr:JSExpr):String	= expr match {
		// literal values
		
		case JSUndefined				=> "undefined"
		case JSNaN						=> "NaN"
		case JSNull						=> "null"
		case JSThis						=> "this"
		
		case JSString(value)			=> value map { JSUtil escapeStringChar (_, true, false) } mkString("\"", "", "\"")
		case JSRegexp(pattern, options)	=> "/" + pattern + "/" + options
		case JSBoolean(value:Boolean)	=> value.toString
		case JSNumber(value:Number)		=> value.toString
		case JSArray(items)				=> items map unparse mkString ("[", ", ", "]")
		case JSObject(items)			=> items map { case (k,v) => unparse(JSString(k)) + ": " + unparse(v) } mkString ("{", ", ", "}")
		
		// arithmetic binary
		
		case JSAdd(left, right)		=> unparse(left) + "+" + unparse(right)
		case JSSub(left, right)		=> unparse(left) + "-" + unparse(right)
		case JSMul(left, right)		=> unparse(left) + "*" + unparse(right)
		case JSDiv(left, right)		=> unparse(left) + "/" + unparse(right)
		case JSMod(left, right)		=> unparse(left) + "%" + unparse(right)

		// arithmetic unary
		
		case JSNeg(sub)	=> "-" + unparse(sub)
		case JSPos(sub)	=> "+" + unparse(sub)
		
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
		
		case JSNot(sub)			=> "!" + unparse(sub)
		case JSAnd(left, right)	=> unparse(left) + "&&"	+ unparse(right)
		case JSOr(left, right)	=> unparse(left) + "||"	+ unparse(right)
		
		// bitwise
		
		case JSBitNot(sub)					=> "~" + unparse(sub)
		case JSBitAnd(left, right)			=> unparse(left) + "&"		+ unparse(right)
		case JSBitOr(left, right)			=> unparse(left) + "|"		+ unparse(right)
		case JSBitXor(left, right)			=> unparse(left) + "^"		+ unparse(right)
		case JSBitLeft(left, right)			=> unparse(left) + "<<"		+ unparse(right)
		case JSBitRight(left, right)		=> unparse(left) + ">>"		+ unparse(right)
		case JSBitRightFill(left, right)	=> unparse(left) + ">>>"	+ unparse(right)
		
		// access
		
		case JSField(name)					=> name
		// TODO must wrap complex lvalues in parens
		case JSDot(lvalue, field)			=> unparse(lvalue) + "." + unparse(field)
		case JSBracket(lvalue, key)			=> unparse(lvalue) + "[" + unparse(key) + "]"

		// function call
		
		case JSNew(call)					=> "new " + unparse(call)
		case JSCall(function, arguments)	=> unparse(function) + (arguments map unparse mkString ("(", ", ", ")"))
		
		// special
		
		case JSParens(sub)					=> "(" + unparse(sub) + ")"
		case JSComma(left, right)			=> unparse(left) + ","	+ unparse(right)
		case JSIn(field, value)				=> unparse(field) + " in "	+ unparse(value)
		case JSTernary(condition, yes, no)	=> unparse(condition) + "?"	+ unparse(yes) + ":" + unparse(no)
	}
}
