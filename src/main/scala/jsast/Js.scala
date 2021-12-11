package jsast

//------------------------------------------------------------------------------
//## newtypes

final case class JsId(value:String)

//------------------------------------------------------------------------------
//## plain js expressions

// groups

sealed trait JsExpr

// literal values

case object JsUndefined	extends JsExpr
case object JsNaN		extends JsExpr
case object JsNull 		extends JsExpr
case object JsThis 		extends JsExpr
case object JsSuper		extends JsExpr

final case class JsBoolean(value:Boolean) 					extends JsExpr
final case class JsNumber(value:Double) 					extends JsExpr
final case class JsString(value:String)						extends JsExpr
final case class JsRegexp(pattern:String, options:String)	extends JsExpr

object JsArray {
	object Var {
		def apply(it:JsExpr*):JsArray					= JsArray(it.toVector)
		def unapplySeq(it:JsArray):Some[Seq[JsExpr]]	= Some(it.items)
	}
}
final case class JsArray(items:Seq[JsExpr]) 				extends JsExpr
object JsObject {
	object Var {
		def apply(it:(String,JsExpr)*):JsObject					= JsObject(it.toVector)
		def unapplySeq(it:JsObject):Some[Seq[(String,JsExpr)]]	= Some(it.items)
	}
}
final case class JsObject(items:Seq[(String,JsExpr)])		extends JsExpr

// operators

final case class JsPrefix(op:JsPrefixOp, child:JsExpr)				extends JsExpr
final case class JsPostfix(op:JsPostfixOp, child:JsExpr)			extends JsExpr
final case class JsInfix(op:JsInfixOp, left:JsExpr, right:JsExpr)	extends JsExpr

// access

final case class JsField(id:JsId)						extends JsExpr
final case class JsSelect(lvalue:JsExpr, field:JsId)	extends JsExpr
final case class JsAccess(lvalue:JsExpr, key:JsExpr)	extends JsExpr

// function call

final case class JsNew(call:JsCall)								extends JsExpr
final case class JsCall(target:JsExpr, arguments:Seq[JsExpr])	extends JsExpr

// special

final case class JsParens(sub:JsExpr)								extends JsExpr
final case class JsIn(field:JsId, value:JsExpr)						extends JsExpr
final case class JsTernary(condition:JsExpr, yes:JsExpr, no:JsExpr)	extends JsExpr

//------------------------------------------------------------------------------
//## operator enums

sealed trait JsPrefixOp
sealed trait JsPostfixOp
sealed trait JsInfixOp

// special

case object JsAssignOp	extends JsInfixOp
case object JsCommaOp	extends JsInfixOp

// logical

case object JsNotOp		extends JsPrefixOp
case object JsAndOp		extends JsInfixOp
case object JsOrOp		extends JsInfixOp

// comparison

case object JsEqOp		extends JsInfixOp
case object JsNeOp		extends JsInfixOp
case object JsEqEqOp	extends JsInfixOp
case object JsNeNeOp	extends JsInfixOp
case object JsGtOp		extends JsInfixOp
case object JsLtOp		extends JsInfixOp
case object JsGeOp		extends JsInfixOp
case object JsLeOp		extends JsInfixOp

// arithmetic

case object JsNegOp	extends JsPrefixOp
case object JsPosOp	extends JsPrefixOp
case object JsAddOp	extends JsInfixOp
case object JsSubOp	extends JsInfixOp
case object JsMulOp	extends JsInfixOp
case object JsDivOp	extends JsInfixOp
case object JsModOp	extends JsInfixOp

// bitwise
case object JsBitNotOp			extends JsPrefixOp
case object JsBitAndOp			extends JsInfixOp
case object JsBitOrOp			extends JsInfixOp
case object JsBitXorOp			extends JsInfixOp
case object JsBitLeftOp			extends JsInfixOp
case object JsBitRightOp		extends JsInfixOp
case object JsBitRightFillOp	extends JsInfixOp

// assignment shortcut

case object JsAddAssignOp	extends JsInfixOp
case object JsSubAssignOp	extends JsInfixOp
case object JsMulAssignOp	extends JsInfixOp
case object JsDivAssignOp	extends JsInfixOp
case object JsModAssignOp	extends JsInfixOp

case object JsBitAndAssignOp		extends JsInfixOp
case object JsBitOrAssignOp			extends JsInfixOp
case object JsBitXorAssignOp		extends JsInfixOp
case object JsBitLeftAssignOp		extends JsInfixOp
case object JsBitRightAssignOp		extends JsInfixOp
case object JsBitRightFillAssignOp	extends JsInfixOp

// sideeffecting

case object JsPreIncrOp		extends JsPrefixOp
case object JsPreDecrOp		extends JsPrefixOp
case object JsPostIncrOp	extends JsPostfixOp
case object JsPostDecrOp	extends JsPostfixOp
