// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTemplateValueParameter extends PsiElement {

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageAssignExpression getAssignExpression();

  @Nullable
  DLanguageTemplateValueParameterDefault getTemplateValueParameterDefault();

  @NotNull
  DLanguageType getType();

  @Nullable
  PsiElement getOpColon();

}
