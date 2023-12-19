package com.craig.scholar.happy.service.codeexchange.freepoly;

import java.util.Collection;

public interface EnumerateFreePolyService<MATRIX> {

  Collection<MATRIX> enumerate(int n);
}
