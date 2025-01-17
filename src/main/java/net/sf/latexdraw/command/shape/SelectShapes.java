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

import io.github.interacto.command.Command;
import java.util.ArrayList;
import java.util.List;
import net.sf.latexdraw.command.DrawingCmdImpl;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapesCmd;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * This command allows to (un-)select shapes.
 * @author Arnaud Blouin
 */
public class SelectShapes extends DrawingCmdImpl implements ShapesCmd, Modifying {
	/** The shapes to handle. */
	private final @NotNull List<Shape> shapes;

	public SelectShapes(final @NotNull Drawing theDrawing) {
		super(theDrawing);
		shapes = new ArrayList<>();
	}

	@Override
	public void doCmdBody() {
		drawing.setSelection(shapes);
	}

	@Override
	public @NotNull RegistrationPolicy getRegistrationPolicy() {
		return RegistrationPolicy.UNLIMITED;
	}

	@Override
	public boolean canDo() {
		return true;
	}

	@Override
	public boolean unregisteredBy(final Command cmd) {
		return cmd instanceof SelectShapes || cmd instanceof CutShapes || cmd instanceof DeleteShapes;
	}

	@Override
	public @NotNull List<Shape> getShapes() {
		return shapes;
	}
}
