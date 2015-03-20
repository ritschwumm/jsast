package jsast

import scala.collection.immutable.{ Seq => ISeq }

//------------------------------------------------------------------------------
//## newtypes

case class JSId(value:String)

//------------------------------------------------------------------------------
//## plain js expressions

// groups

sealed trait JSExpr

// literal values

case object JSUndefined	extends JSExpr
case object JSNaN		extends JSExpr
case object JSNull 		extends JSExpr
case object JSThis 		extends JSExpr
case object JSSuper		extends JSExpr

case class JSBoolean(value:Boolean) 				extends JSExpr
case class JSNumber(value:Double) 					extends JSExpr
case class JSString(value:String)					extends JSExpr
case class JSRegexp(pattern:String, options:String)	extends JSExpr
case class JSArray(items:ISeq[JSExpr]) 				extends JSExpr
case class JSObject(items:ISeq[(String,JSExpr)])	extends JSExpr

// operators

case class JSUnary(op:JSUnaryOp, child:JSExpr)					extends JSExpr
case class JSBinary(op:JSBinaryOp, left:JSExpr, right:JSExpr)	extends JSExpr

// access

case class JSField(id:JSId)						extends JSExpr
case class JSSelect(lvalue:JSExpr, field:JSId)	extends JSExpr
case class JSAccess(lvalue:JSExpr, key:JSExpr)	extends JSExpr

// function call
		
case class JSNew(call:JSCall)								extends JSExpr
case class JSCall(target:JSExpr, arguments:ISeq[JSExpr])	extends JSExpr

// special

case class JSParens(sub:JSExpr)					extends JSExpr
case class JSComma(left:JSExpr, right:JSExpr)	extends JSExpr

case class JSIn(field:JSField, value:JSExpr)					extends JSExpr
case class JSTernary(condition:JSExpr, yes:JSExpr, no:JSExpr)	extends JSExpr

//------------------------------------------------------------------------------
//## varargs builder

object JSVarArray {
	def apply(it:JSExpr*):JSArray					= JSArray(it.toVector)
	def unapplySeq(it:JSArray):Option[ISeq[JSExpr]]	= Some(it.items)
}

object JSVarObject {
	def apply(it:(String,JSExpr)*):JSObject						= JSObject(it.toVector)
	def unapplySeq(it:JSObject):Option[ISeq[(String,JSExpr)]]	= Some(it.items)
}

//------------------------------------------------------------------------------
//## operator enums

sealed trait JSUnaryOp
sealed trait JSBinaryOp

// arithmetic binary

case object JSAddOp	extends JSBinaryOp
case object JSSubOp	extends JSBinaryOp
case object JSMulOp	extends JSBinaryOp
case object JSDivOp	extends JSBinaryOp
case object JSModOp	extends JSBinaryOp

// arithmetic unary

case object JSNegOp	extends JSUnaryOp
case object JSPosOp	extends JSUnaryOp

// comparison

case object JSEqOp		extends JSBinaryOp
case object JSNeOp		extends JSBinaryOp
case object JSEqEqOp	extends JSBinaryOp
case object JSNeNeOp	extends JSBinaryOp
case object JSGtOp		extends JSBinaryOp
case object JSLtOp		extends JSBinaryOp
case object JSGeOp		extends JSBinaryOp
case object JSLeOp		extends JSBinaryOp

// logical

case object JSNotOp		extends JSUnaryOp
case object JSAndOp		extends JSBinaryOp
case object JSOrOp		extends JSBinaryOp

// bitwise

case object JSBitNotOp			extends JSUnaryOp
case object JSBitAndOp			extends JSBinaryOp
case object JSBitOrOp			extends JSBinaryOp
case object JSBitXorOp			extends JSBinaryOp
case object JSBitLeftOp			extends JSBinaryOp
case object JSBitRightOp		extends JSBinaryOp
case object JSBitRightFillOp	extends JSBinaryOp

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
