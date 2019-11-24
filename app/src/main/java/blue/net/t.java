package blue.net;

public class t {
    /* JADX WARNING: type inference failed for: r3v1, types: [int, java.lang.String] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v1, types: [int, java.lang.String]
  assigns: [java.lang.String]
  uses: [int]
  mth insns count: 25
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void rip() {
        /*
            r8 = 1
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "lynx/blue"
            r0.<init>(r1)
            java.lang.StringBuilder r1 = r0.reverse()
            r1.toString()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "eulb/kik"
            r1.<init>(r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Blue Kik"
            java.lang.String r3 = blue.luminosity.resourceRetriever.getString()
            r2.<init>(r3)
            java.lang.StringBuilder r3 = r2.reverse()
            r3.toString()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "kiK eulB"
            r3.<init>(r4)
            java.lang.String r4 = r0.toString()
            java.lang.String r5 = r1.toString()
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x003e
            r8 = 0
        L_0x003e:
            java.lang.String r5 = r3.toString()
            java.lang.String r6 = r2.toString()
            boolean r5 = r5.equals(r6)
            if (r5 != 0) goto L_0x004d
            r8 = 0
        L_0x004d:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: blue.net.t.rip():void");
    }
}
