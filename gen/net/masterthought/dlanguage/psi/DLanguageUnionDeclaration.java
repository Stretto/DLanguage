// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageUnionDeclaration extends PsiElement {

  @Nullable
  DLanguageConstraint getConstraint();

  @Nullable
  DLanguageStructBody getStructBody();

  @Nullable
  DLanguageTemplateParameters getTemplateParameters();

  @Nullable
  PsiElement getIdentifier();

  @NotNull
  PsiElement getKwUnion();

  @Nullable
  PsiElement getSemicolon();

}