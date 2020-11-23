package jsast

object JsUnparser {
	def unparseExpr(expr:JsExpr):String	=
			expr match {
				// literal values

				case JsUndefined					=> "undefined"
				case JsNaN							=> "NaN"
				case JsNull							=> "null"
				case JsThis							=> "this"
				case JsSuper						=> "super"

				case JsString(value)				=> JsUtil stringLiteralDQ value
				case JsRegexp(pattern, options)		=> "/" + pattern + "/" + options
				case JsBoolean(value)				=> if (value) "true" else "false"
				case JsNumber(value)				=> value.toString
				case JsArray(items)					=> items.map(unparseExpr).mkString("[", ",", "]")
				case JsObject(items)				=> items.map { case (k,v) => (JsUtil stringLiteralDQ k) + ":" + unparseExpr(v) } .mkString("{", ",", "}")

				// operators

				case JsPrefix(op, child)			=> unparsePrefixOp(op) + unparseExpr(child)
				case JsPostfix(op, child)			=> unparseExpr(child) + unparsePostfixOp(op)
				case JsInfix(op, left, right)		=> unparseExpr(left) + unparseInfixOp(op) + unparseExpr(right)

				// access

				case JsField(id)					=> unparseId(id)
				case JsSelect(lvalue, field)		=> unparseExpr(lvalue) + "." + unparseId(field)
				case JsAccess(lvalue, key)			=> unparseExpr(lvalue) + "[" + unparseExpr(key) + "]"

				// function call

				case JsNew(call)					=> "new " + unparseExpr(call)
				case JsCall(target, arguments)		=> unparseExpr(target) + arguments.map(unparseExpr).mkString ("(", ",", ")")

				// special

				case JsParens(sub)					=> "(" + unparseExpr(sub) + ")"
				case JsIn(field, value)				=> unparseId(field) + " in "	+ unparseExpr(value)
				case JsTernary(condition, yes, no)	=> unparseExpr(condition) + "?"	+ unparseExpr(yes) + ":" + unparseExpr(no)
			}

	def unparseId(id:JsId):String	= id.value

	def unparsePrefixOp(op:JsPrefixOp):String	=
			op match {
				case JsNotOp		=> "!"

				case JsNegOp		=> "-"
				case JsPosOp		=> "+"

				case JsBitNotOp		=> "~"

				case JsPreIncrOp	=> "++"
				case JsPreDecrOp	=> "--"
			}

	def unparsePostfixOp(op:JsPostfixOp):String	=
			op match {
				case JsPostIncrOp	=> "++"
				case JsPostDecrOp	=> "--"
			}

	def unparseInfixOp(op:JsInfixOp):String	=
			op match {
				case JsAssignOp				=> "="
				case JsCommaOp				=> ","

				case JsAndOp				=> "&&"
				case JsOrOp					=> "||"

				case JsEqOp					=> "=="
				case JsNeOp					=> "!="
				case JsEqEqOp				=> "==="
				case JsNeNeOp				=> "!=="
				case JsGtOp					=> ">"
				case JsLtOp					=> "<"
				case JsGeOp					=> ">="
				case JsLeOp					=> "<="

				case JsAddOp				=> "+"
				case JsSubOp				=> "-"
				case JsMulOp				=> "*"
				case JsDivOp				=> "/"
				case JsModOp				=> "%"

				case JsBitAndOp				=> "&"
				case JsBitOrOp				=> "|"
				case JsBitXorOp				=> "^"
				case JsBitLeftOp			=> "<<"
				case JsBitRightOp			=> ">>"
				case JsBitRightFillOp		=> ">>>"

				case JsAddAssignOp			=> "+="
				case JsSubAssignOp			=> "-="
				case JsMulAssignOp			=> "*="
				case JsDivAssignOp			=> "/="
				case JsModAssignOp			=> "%="

				case JsBitAndAssignOp		=> "&="
				case JsBitOrAssignOp		=> "|="
				case JsBitXorAssignOp		=> "^="
				case JsBitLeftAssignOp		=> "<<="
				case JsBitRightAssignOp		=> ">>="
				case JsBitRightFillAssignOp	=> ">>>="
			}
}
