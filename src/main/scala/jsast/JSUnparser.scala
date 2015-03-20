package jsast

object JSUnparser {
	def unparseExpr(expr:JSExpr):String	=
			expr match {
				// literal values
				
				case JSUndefined					=> "undefined"
				case JSNaN							=> "NaN"
				case JSNull							=> "null"
				case JSThis							=> "this"
				case JSSuper						=> "super"
				
				case JSString(value)				=> JSUtil stringLiteralDQ value
				case JSRegexp(pattern, options)		=> "/" + pattern + "/" + options
				case JSBoolean(value)				=> if (value) "true" else "false"
				case JSNumber(value)				=> value.toString
				case JSArray(items)					=> items map unparseExpr mkString ("[", ",", "]")
				case JSObject(items)				=> items map { case (k,v) => (JSUtil stringLiteralDQ k) + ":" + unparseExpr(v) } mkString ("{", ",", "}")
				
				// operators
				
				case JSUnary(op, child)				=> unparseUnaryOp(op) + unparseExpr(child)
				case JSBinary(op, left, right)		=> unparseExpr(left) + unparseBinaryOp(op) + unparseExpr(right)
				
				// access
				
				case JSField(id)					=> unparseId(id)
				case JSSelect(lvalue, field)		=> unparseExpr(lvalue) + "." + unparseId(field)
				case JSAccess(lvalue, key)			=> unparseExpr(lvalue) + "[" + unparseExpr(key) + "]"
		
				// function call
				
				case JSNew(call)					=> "new " + unparseExpr(call)
				case JSCall(target, arguments)		=> unparseExpr(target) + (arguments map unparseExpr mkString ("(", ",", ")"))
				
				// special
				
				case JSParens(sub)					=> "(" + unparseExpr(sub) + ")"
				case JSComma(left, right)			=> unparseExpr(left) + ","	+ unparseExpr(right)
				case JSIn(field, value)				=> unparseExpr(field) + " in "	+ unparseExpr(value)
				case JSTernary(condition, yes, no)	=> unparseExpr(condition) + "?"	+ unparseExpr(yes) + ":" + unparseExpr(no)
			}
			
	def unparseId(id:JSId):String	= id.value
			
	def unparseUnaryOp(op:JSUnaryOp):String	=
			op match {
				case JSNegOp		=> "-"
				case JSPosOp		=> "+"
				
				case JSNotOp		=> "!"
				
				case JSBitNotOp		=> "~"
			}
			
	def unparseBinaryOp(op:JSBinaryOp):String	=
			op match {
				case JSAddOp			=> "+"
				case JSSubOp			=> "-"
				case JSMulOp			=> "*"
				case JSDivOp			=> "/"
				case JSModOp			=> "%"
				
				case JSEqOp				=> "=="
				case JSNeOp				=> "!="
				case JSEqEqOp			=> "==="
				case JSNeNeOp			=> "!=="
				case JSGtOp				=> ">"
				case JSLtOp				=> "<"
				case JSGeOp				=> ">="
				case JSLeOp				=> "<="
				
				case JSAndOp			=> "&&"
				case JSOrOp				=> "||"
				
				case JSBitAndOp			=> "&"
				case JSBitOrOp			=> "|"
				case JSBitXorOp			=> "^"
				case JSBitLeftOp		=> "<<"
				case JSBitRightOp		=> ">>"
				case JSBitRightFillOp	=> ">>>"
			}
}
