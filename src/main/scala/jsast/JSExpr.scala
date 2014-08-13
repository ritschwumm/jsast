package jsast

import scala.collection.immutable.{ Seq => ISeq }

// groups

sealed trait JSExpr

sealed trait JSLiteral		extends JSExpr

sealed trait JSOperator		extends JSExpr

sealed trait JSPrefix		extends JSOperator
sealed trait JSSuffix		extends JSOperator
sealed trait JSInfix		extends JSOperator
sealed trait JSCircumfix	extends JSOperator

sealed trait JSAccess 		extends JSExpr

// literal values

case object JSUndefined	extends JSLiteral
case object JSNaN		extends JSLiteral
case object JSNull 		extends JSLiteral
case object JSThis 		extends JSLiteral

case class JSBoolean(value:Boolean) 				extends JSLiteral
case class JSNumber(value:Number) 					extends JSLiteral
case class JSString(value:String)					extends JSLiteral
case class JSRegexp(pattern:String, options:String)	extends JSLiteral
case class JSArray(items:ISeq[JSExpr]) 				extends JSLiteral
case class JSObject(items:ISeq[(String,JSExpr)])		extends JSLiteral

// arithmetic binary

case class JSAdd(left:JSExpr, right:JSExpr)	extends JSInfix
case class JSSub(left:JSExpr, right:JSExpr)	extends JSInfix
case class JSMul(left:JSExpr, right:JSExpr)	extends JSInfix
case class JSDiv(left:JSExpr, right:JSExpr)	extends JSInfix
case class JSMod(left:JSExpr, right:JSExpr)	extends JSInfix

// arithmetic unary

case class JSNeg(sub:JSExpr)	extends JSPrefix
case class JSPos(sub:JSExpr)	extends JSPrefix

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

case class JSNot(sub:JSExpr)				extends JSPrefix
case class JSAnd(left:JSExpr, right:JSExpr)	extends JSInfix
case class JSOr(left:JSExpr, right:JSExpr)	extends JSInfix

// bitwise

case class JSBitNot(sub:JSExpr)							extends JSPrefix
case class JSBitAnd(left:JSExpr, right:JSExpr)			extends JSInfix
case class JSBitOr(left:JSExpr, right:JSExpr)			extends JSInfix
case class JSBitXor(left:JSExpr, right:JSExpr)			extends JSInfix
case class JSBitLeft(left:JSExpr, right:JSExpr)			extends JSInfix
case class JSBitRight(left:JSExpr, right:JSExpr)		extends JSInfix
case class JSBitRightFill(left:JSExpr, right:JSExpr)	extends JSInfix

// access

case class JSField(name:String)					extends JSAccess
case class JSDot(lvalue:JSExpr, field:JSField)	extends JSAccess
case class JSBracket(lvalue:JSExpr, key:JSExpr)	extends JSAccess

// function call
		
case class JSNew(call:JSCall)									extends JSExpr
case class JSCall(function:JSAccess, arguments:ISeq[JSExpr])	extends JSExpr

// special

case class JSParens(sub:JSExpr)					extends JSCircumfix
case class JSComma(left:JSExpr, right:JSExpr)	extends JSInfix

case class JSIn(field:JSField, value:JSExpr)					extends JSInfix
case class JSTernary(condition:JSExpr, yes:JSExpr, no:JSExpr)	extends JSOperator

//------------------------------------------------------------------------------

// varargs builder

object JSVarArray {
	def apply(it:JSExpr*):JSArray					= JSArray(it.toVector)
	def unapplySeq(it:JSArray):Option[ISeq[JSExpr]]	= Some(it.items)
}

object JSVarObject {
	def apply(it:(String,JSExpr)*):JSObject						= JSObject(it.toVector)
	def unapplySeq(it:JSObject):Option[ISeq[(String,JSExpr)]]	= Some(it.items)
}

//------------------------------------------------------------------------------

/*
// pre/post increment/decrement

sealed trait JSInline extends JSOperator

case class JSPreIncr(variable:String)	extends JSPrefix
case class JSPreDecr(variable:String)	extends JSPrefix
case class JSPostIncr(variable:String)	extends JSSuffix
case class JSPostDecr(variable:String)	extends JSSuffix

// setter

sealed trait JSSet	extends JSOperator

case class JSAssign(variable:String, value:JSExpr)		extends JSSet
case class JSAddAssign(variable:String, value:JSExpr)	extends JSSet
case class JSSubAssign(variable:String, value:JSExpr)	extends JSSet
case class JSMulAssign(variable:String, value:JSExpr)	extends JSSet
case class JSDivAssign(variable:String, value:JSExpr)	extends JSSet
case class JSModAssign(variable:String, value:JSExpr)	extends JSSet
*/
