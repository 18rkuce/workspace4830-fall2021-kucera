
public aspect TraceAspectKucera {

	 pointcut classToTrace(): within(*App);
	 
	   //pointcut methodToTrace():  classToTrace() &&  execution(String area(void));
	 pointcut methodToTrace():  classToTrace() &&  execution(* *App.get*(..));

	   before(): methodToTrace() {
	      String info = thisJoinPointStaticPart.getSignature() + ", " //
	            + thisJoinPointStaticPart.getSourceLocation().getFileName() + ", " //
	            + thisJoinPointStaticPart.getSourceLocation().getLine();
	      System.out.println("[BGN]" + info);
	   }

	   after(): methodToTrace() {
	      System.out.println("[END]" + thisJoinPointStaticPart.getSignature());
	   }
}
