/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.command.shape;

import io.github.interacto.undo.Undoable;
import java.util.ResourceBundle;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapeCmdImpl;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * This command mirrors a shape.
 * @author Arnaud Blouin
 */
public class MirrorShapes extends ShapeCmdImpl<Shape> implements Undoable, Modifying {
	/** If true, the mirroring is horizontal. */
	private final boolean horizontally;


	public MirrorShapes(final boolean horizontally, final @NotNull Shape sh) {
		super(sh);
		this.horizontally = horizontally;
	}

	@Override
	protected void doCmdBody() {
		if(horizontally) {
			shape.mirrorHorizontal(shape.getGravityCentre().getX());
		}else {
			shape.mirrorVertical(shape.getGravityCentre().getY());
		}
		shape.setModified(true);
	}

	@Override
	public void undo() {
		doCmdBody();
	}

	@Override
	public void redo() {
		doCmdBody();
	}

	@Override
	public @NotNull  String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("Actions.7");
	}
}
