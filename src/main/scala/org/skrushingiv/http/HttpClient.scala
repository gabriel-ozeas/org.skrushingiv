package org.skrushingiv.http

import scala.concurrent.{ExecutionContext, Future}
import play.api.http.{Status, ContentTypeOf, Writeable}
import play.api.libs.ws._
import play.api.Application

/**
 * Sends HTTP requests to a server.
 * 
 * This trait's purpose is to 
 */
trait HttpClient {

  /**
   * The base url - should include protocol, host, port and any base path components. Should not include a trailing slash.
   */
  def rootUrl : String

  /**
   * Executes GET.
   */
  final def get(path: String, params: PSeq = Seq.empty, headers: PSeq = Seq.empty)(implicit app: Application, ec: ExecutionContext): Future[WSResponse] = {
    execute(path, headers, params, _.get)
  }

  /**
   * Executes HEAD.
   */
  final def head(path: String, params: PSeq = Seq.empty, headers: PSeq = Seq.empty)(implicit app: Application, ec: ExecutionContext): Future[WSResponse] = {
    execute(path, headers, params, _.head)
  }

  /**
   * Executes OPTIONS.
   */
  final def options(path: String, params: PSeq = Seq.empty, headers: PSeq = Seq.empty)(implicit app: Application, ec: ExecutionContext): Future[WSResponse] = {
    execute(path, headers, params, _.options)
  }

  /**
   * Executes POST.
   */
  final def post[T](path: String, body: T, params: PSeq = Seq.empty, headers: PSeq = Seq.empty)(implicit app: Application, wrt: Writeable[T], ct: ContentTypeOf[T], ec: ExecutionContext): Future[WSResponse] = {
    execute(path, headers, params, _.post(body))
  }

  /**
   * Executes PUT.
   */
  final def put[T](path: String, body: T, params: PSeq = Seq.empty, headers: PSeq = Seq.empty)(implicit app: Application, wrt: Writeable[T], ct: ContentTypeOf[T], ec: ExecutionContext): Future[WSResponse] = {
    execute(path, headers, params, _.put(body))
  }

  /**
   * Executes DELETE.
   */
  final def delete(path: String, params: PSeq = Seq.empty, headers: PSeq = Seq.empty)(implicit app: Application, ec: ExecutionContext): Future[WSResponse] = {
    execute(path, headers, params, _.delete)
  }

  /**
   * Prepares and executes the request.
   */
  final private def execute(path: String, params: PSeq, headers: PSeq, invoke: WSRequestHolder => Future[WSResponse])(implicit app: Application, ec: ExecutionContext): Future[WSResponse] = {
    val r = WS.url(rootUrl + "/" + path).withHeaders(headers:_*).withQueryString(params:_*)
    (prepare _ andThen invoke andThen after)(r) 
  }


  /**
   * Transforms the request holder before executing. Use this to apply AuthSchemes or signatures.
   * The default implementation simply passes the argument through.
   */
  protected def prepare(req: WSRequest): WSRequest = req

  /**
   * Transforms the response after the call has been executed.
   * The default implementation simply passes the argument through.
   */
  protected def after(response: Future[WSResponse]): Future[WSResponse] = response
}