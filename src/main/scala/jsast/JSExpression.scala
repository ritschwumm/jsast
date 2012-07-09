package jsast

sealed trait JSExpr

// case class JSUnparsed(code:String)	extends JSExpr

// literal values

case object JSUndefined	extends JSExpr
case object JSNaN		extends JSExpr
case object JSNull 		extends JSExpr

case class JSBoolean(value:Boolean) 					extends JSExpr
case class JSNumber(value:Number) 						extends JSExpr
case class JSString(value:String)						extends JSExpr
case class JSRegexp(pattern:String, options:String)		extends JSExpr
case class JSArray(items:Seq[JSExpr]) 					extends JSExpr
case class JSObject(items:Seq[(String,JSExpr)])			extends JSExpr

// operators

sealed trait JSOperator		extends JSExpr

sealed trait JSPrefix		extends JSOperator
sealed trait JSSuffix		extends JSOperator
sealed trait JSInfix		extends JSOperator
sealed trait JSCircumfix	extends JSOperator

// arithmetic binary

case class JSAdd(left:JSExpr, right:JSExpr)	extends JSInfix
case class JSSub(left:JSExpr, right:JSExpr)	extends JSInfix
case class JSMul(left:JSExpr, right:JSExpr)	extends JSInfix
case class JSDiv(left:JSExpr, right:JSExpr)	extends JSInfix
case class JSMod(left:JSExpr, right:JSExpr)	extends JSInfix

// arithmetic unary

case class JSNeg(sub:JSExpr)				extends JSPrefix
case class JSPos(sub:JSExpr)				extends JSPrefix

// comparison

case class JSEq(left:JSExpr, right:JSExpr)		extends JSInfix
case class JSNe(left:JSExpr, right:JSExpr)		extends JSInfix
case class JSEqEq(left:JSExpr, right:JSExpr)	extends JSInfix
case class JSNeNe(left:JSExpr, right:JSExpr)	extends JSInfix
case class JSGt(left:JSExpr, right:JSExpr)		extends JSInfix
case class JSLt(left:JSExpr, right:JSExpr)		extends JSInfix
case class JSGe(left:JSExpr, right:JSExpr)		extends JSInfix
case class JSLe(left:JSExpr, right:JSExpr)		extends JSInfix

// logical

case class JSNot(sub:JSExpr)					extends JSPrefix
case class JSAnd(left:JSExpr, right:JSExpr)		extends JSInfix
case class JSOr(left:JSExpr, right:JSExpr)		extends JSInfix

// bitwise

case class JSBitNot(sub:JSExpr)							extends JSPrefix
case class JSBitAnd(left:JSExpr, right:JSExpr)			extends JSInfix
case class JSBitOr(left:JSExpr, right:JSExpr)			extends JSInfix
case class JSBitXor(left:JSExpr, right:JSExpr)			extends JSInfix
case class JSBitLeft(left:JSExpr, right:JSExpr)			extends JSInfix
case class JSBitRight(left:JSExpr, right:JSExpr)		extends JSInfix
case class JSBitRightFill(left:JSExpr, right:JSExpr)	extends JSInfix

// special

case class JSIn(left:JSExpr, right:JSExpr)				extends JSInfix

case class JSTernary(condition:JSExpr, trueCase:JSExpr, falseCase:JSExpr)	extends JSOperator

case class JSParens(sub:JSExpr)					extends JSCircumfix

case class JSComma(left:JSExpr, right:JSExpr)	extends JSInfix

case class JSDeref(lvalue:JSExpr, name:String)	extends JSExpr
case class JSAccess(lvalue:JSExpr, key:JSExpr)	extends JSExpr

case class JSNew(call:JSCall)								extends JSExpr
case class JSCall(function:String, arguments:Seq[JSExpr])	extends JSExpr

/*
// pre/post increment/decrement

sealed trait JSInline extends JSOperator

case class JSPreIncr(variable:String)	extends JSPrefix
case class JSPreDecr(variable:String)	extends JSPrefix
case class JSPostIncr(variable:String)	extends JSSuffix
case class JSPostDecr(variable:String)	extends JSSuffix

// setter

sealed trait JSSet		extends JSOperator

case class JSAssign(variable:String, value:JSExpr)		extends JSSet
case class JSAddAssign(variable:String, value:JSExpr)	extends JSSet
case class JSSubAssign(variable:String, value:JSExpr)	extends JSSet
case class JSMulAssign(variable:String, value:JSExpr)	extends JSSet
case class JSDivAssign(variable:String, value:JSExpr)	extends JSSet
case class JSModAssign(variable:String, value:JSExpr)	extends JSSet
*/

//------------------------------------------------------------------------------

object JSVarArray {
	def apply(it:JSExpr*):JSArray					= JSArray(it)
	def unapplySeq(it:JSArray):Option[Seq[JSExpr]]	= Some(it.items)
}

object JSVarObject {
	def apply(it:(String,JSExpr)*):JSObject						= JSObject(it)
	def unapplySeq(it:JSObject):Option[Seq[(String,JSExpr)]]	= Some(it.items)
}
