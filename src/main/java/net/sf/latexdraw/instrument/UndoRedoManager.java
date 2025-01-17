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
package net.sf.latexdraw.instrument;

import io.github.interacto.command.library.Redo;
import io.github.interacto.command.library.Undo;
import io.github.interacto.jfx.undo.FXUndoCollector;
import io.github.interacto.undo.UndoCollector;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.MagneticGrid;
import org.jetbrains.annotations.NotNull;

/**
 * This instrument allows to undo and redo saved commands.
 * This instrument provides two buttons to interact with and two shortcuts (ctrl/apple-Z, ctrl-apple-Y).
 * @author Arnaud Blouin
 */
public class UndoRedoManager extends CanvasInstrument implements Initializable {
	/** The button used to undo commands. */
	@FXML private Button undoB;
	/** The button used to redo commands. */
	@FXML private Button redoB;
	private final @NotNull ResourceBundle lang;

	@Inject
	public UndoRedoManager(final Canvas canvas, final MagneticGrid grid, final ResourceBundle lang) {
		super(canvas, grid);
		this.lang = Objects.requireNonNull(lang);
		UndoCollector.INSTANCE.addHandler(this);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);

		undoB.disableProperty().bind(FXUndoCollector.INSTANCE.lastUndoProperty().isNull());
		redoB.disableProperty().bind(FXUndoCollector.INSTANCE.lastRedoProperty().isNull());
		undoB.tooltipProperty().bind(Bindings.createObjectBinding(() -> UndoCollector.INSTANCE.getLastUndo().
			map(undo -> new Tooltip(undo.getUndoName(lang))).orElse(null), FXUndoCollector.INSTANCE.lastUndoProperty()));
		redoB.tooltipProperty().bind(Bindings.createObjectBinding(() -> UndoCollector.INSTANCE.getLastRedo().
			map(redo -> new Tooltip(redo.getUndoName(lang))).orElse(null), FXUndoCollector.INSTANCE.lastRedoProperty()));
	}

	@Override
	protected void configureBindings() {
		buttonBinder(Undo::new).on(undoB).bind();
		buttonBinder(Redo::new).on(redoB).bind();
		keyNodeBinder(Undo::new).on(canvas).with(KeyCode.Z, SystemUtils.getInstance().getControlKey()).bind();
		keyNodeBinder(Redo::new).on(canvas).with(KeyCode.Y, SystemUtils.getInstance().getControlKey()).bind();
	}

	@Override
	public void setActivated(final boolean act) {
		super.setActivated(act);

		undoB.setVisible(act);
		redoB.setVisible(act);
	}
}
