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
				
				case JSPrefix(op, child)			=> unparsePrefixOp(op) + unparseExpr(child)
				case JSPostfix(op, child)			=> unparseExpr(child) + unparsePostfixOp(op)
				case JSInfix(op, left, right)		=> unparseExpr(left) + unparseInfixOp(op) + unparseExpr(right)
				
				// access
				
				case JSField(id)					=> unparseId(id)
				case JSSelect(lvalue, field)		=> unparseExpr(lvalue) + "." + unparseId(field)
				case JSAccess(lvalue, key)			=> unparseExpr(lvalue) + "[" + unparseExpr(key) + "]"
		
				// function call
				
				case JSNew(call)					=> "new " + unparseExpr(call)
				case JSCall(target, arguments)		=> unparseExpr(target) + (arguments map unparseExpr mkString ("(", ",", ")"))
				
				// special
				
				case JSParens(sub)					=> "(" + unparseExpr(sub) + ")"
				case JSIn(field, value)				=> unparseId(field) + " in "	+ unparseExpr(value)
				case JSTernary(condition, yes, no)	=> unparseExpr(condition) + "?"	+ unparseExpr(yes) + ":" + unparseExpr(no)
			}
			
	def unparseId(id:JSId):String	= id.value
			
	def unparsePrefixOp(op:JSPrefixOp):String	=
			op match {
				case JSNotOp		=> "!"
				
				case JSNegOp		=> "-"
				case JSPosOp		=> "+"
				
				case JSBitNotOp		=> "~"
				
				case JSPreIncrOp	=> "++"
				case JSPreDecrOp	=> "--"
			}
			
	def unparsePostfixOp(op:JSPostfixOp):String	=
			op match {
				case JSPostIncrOp	=> "++"
				case JSPostDecrOp	=> "--"
			}
			
	def unparseInfixOp(op:JSInfixOp):String	=
			op match {
				case JSAssignOp				=> "="
				case JSCommaOp				=> ","
				
				case JSAndOp				=> "&&"
				case JSOrOp					=> "||"
				
				case JSEqOp					=> "=="
				case JSNeOp					=> "!="
				case JSEqEqOp				=> "==="
				case JSNeNeOp				=> "!=="
				case JSGtOp					=> ">"
				case JSLtOp					=> "<"
				case JSGeOp					=> ">="
				case JSLeOp					=> "<="
				
				case JSAddOp				=> "+"
				case JSSubOp				=> "-"
				case JSMulOp				=> "*"
				case JSDivOp				=> "/"
				case JSModOp				=> "%"
				
				case JSBitAndOp				=> "&"
				case JSBitOrOp				=> "|"
				case JSBitXorOp				=> "^"
				case JSBitLeftOp			=> "<<"
				case JSBitRightOp			=> ">>"
				case JSBitRightFillOp		=> ">>>"
				
				case JSAddAssignOp			=> "+="
				case JSSubAssignOp			=> "-="
				case JSMulAssignOp			=> "*="
				case JSDivAssignOp			=> "/="
				case JSModAssignOp			=> "%="
				
				case JSBitAndAssignOp		=> "&="
				case JSBitOrAssignOp		=> "|="
				case JSBitXorAssignOp		=> "^="
				case JSBitLeftAssignOp		=> "<<="
				case JSBitRightAssignOp		=> ">>="
				case JSBitRightFillAssignOp	=> ">>>="
			}
}
