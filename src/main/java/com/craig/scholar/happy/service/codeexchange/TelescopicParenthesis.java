package com.craig.scholar.happy.service.codeexchange;

public class TelescopicParenthesis implements HappyCoding {

    @Override
    public void execute() {

        execute("(())()");
    }

    private void execute(String s) {
        int c = 0;
        for (int i = 0; i<s.length(); i++) {
            boolean l = s.charAt(i) == '(';
            System.out.print("\n".repeat(l ? c : c-1) + (i<1 ? "" : " ".repeat(i)) + s.charAt(i) + "\r");
            if (l) c++;
            if (!l) c--;
        }
    }
}
