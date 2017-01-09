/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.IStdGridProp;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IStandardGrid;

/**
 * A implementation of an abstract latex grid.
 */
abstract class LAbstractGrid extends LPositionShape implements IStandardGrid {
	/** If true, the x label will be displayed at the south of the grid. Else at the north */
	protected boolean xLabelSouth;
	/** If true, the y label will be displayed at the west of the grid. Else at the east */
	protected boolean yLabelWest;
	/** The x-minimum values of the axes */
	protected double gridStartx;
	/** The y-minimum values of the axes */
	protected double gridStarty;
	/** The x-maximum values of the axes */
	protected double gridEndx;
	/** The y-maximum values of the axes */
	protected double gridEndy;
	/** The x-coordinate of the origin of the grid */
	protected double originx;
	/** The y-coordinate of the origin of the grid */
	protected double originy;
	/** The size of the labels. */
	protected int labelSize;


	/**
	 * Creates an abstract grid.
	 * @param pt The position
	 */
	LAbstractGrid(final IPoint pt) {
		super(pt);
		xLabelSouth = true;
		yLabelWest = true;
		originx = 0.0;
		originy = 0.0;
		gridStartx = 0.0;
		gridStarty = 0.0;
		gridEndx = 2.0;
		gridEndy = 2.0;
		labelSize = 10;
	}


	@Override
	public double getGridMinX() {
		return gridEndx < gridStartx ? gridEndx : gridStartx;
	}


	@Override
	public double getGridMaxX() {
		return gridEndx >= gridStartx ? gridEndx : gridStartx;
	}


	@Override
	public double getGridMinY() {
		return gridEndy < gridStarty ? gridEndy : gridStarty;
	}


	@Override
	public double getGridMaxY() {
		return gridEndy >= gridStarty ? gridEndy : gridStarty;
	}


	@Override
	public IPoint getBottomRightPoint() {
		final IPoint pos = getPosition();
		return ShapeFactory.INST.createPoint(pos.getX() + getGridMaxX() * PPC, pos.getY() - getGridMinY() * PPC);
	}


	@Override
	public IPoint getTopLeftPoint() {
		final IPoint pos = getPosition();
		return ShapeFactory.INST.createPoint(pos.getX() + getGridMinX() * PPC, pos.getY() - getGridMaxY() * PPC);
	}


	@Override
	public IPoint getTopRightPoint() {
		final IPoint pos = getPosition();
		final double step = getStep();
		//FIXME strange: different from getTopLeftPoint and co. but works for scale.
		return ShapeFactory.INST.createPoint(pos.getX() + step * (gridEndx - gridStartx), pos.getY() - step * (gridEndy - gridStarty));
	}


	@Override
	public void mirrorHorizontal(final IPoint origin) {
		if(origin == null) return;

		final IPoint bl = points.get(0).horizontalSymmetry(origin);
		final IPoint br = getBottomRightPoint().horizontalSymmetry(origin);

		points.get(0).setPoint(br.getX() < bl.getX() ? br.getX() : bl.getX(), br.getY());
	}


	@Override
	public void mirrorVertical(final IPoint origin) {
		if(origin == null) return;

		final IPoint bl = points.get(0).verticalSymmetry(origin);
		final IPoint tl = getTopLeftPoint().verticalSymmetry(origin);

		points.get(0).setPoint(bl.getX(), bl.getY() > tl.getY() ? bl.getY() : tl.getY());
	}


	@Override
	public void setLabelsSize(final int labelsSize) {
		if(labelsSize >= 0) {
			labelSize = labelsSize;
		}
	}


	@Override
	public double getGridEndX() {
		return gridEndx;
	}


	@Override
	public double getGridEndY() {
		return gridEndy;
	}

	@Override
	public double getGridStartX() {
		return gridStartx;
	}


	@Override
	public double getGridStartY() {
		return gridStarty;
	}


	@Override
	public int getLabelsSize() {
		return labelSize;
	}


	@Override
	public double getOriginX() {
		return originx;
	}

	@Override
	public double getOriginY() {
		return originy;
	}


	@Override
	public void setGridEnd(final double x, final double y) {
		setGridEndX(x);
		setGridEndY(y);
	}


	@Override
	public void setGridEndX(final double x) {
		if(x >= gridStartx && MathUtils.INST.isValidCoord(x)) {
			gridEndx = x;
		}
	}


	@Override
	public void setGridEndY(final double y) {
		if(y >= gridStarty && MathUtils.INST.isValidCoord(y)) {
			gridEndy = y;
		}
	}


	@Override
	public void setGridStart(final double x, final double y) {
		setGridStartX(x);
		setGridStartY(y);
	}


	@Override
	public void setGridStartX(final double x) {
		if(x <= gridEndx && MathUtils.INST.isValidCoord(x)) {
			gridStartx = x;
		}
	}


	@Override
	public void setGridStartY(final double y) {
		if(y <= gridEndy && MathUtils.INST.isValidCoord(y)) {
			gridStarty = y;
		}
	}


	@Override
	public void setOrigin(final double x, final double y) {
		setOriginX(x);
		setOriginY(y);
	}


	@Override
	public void setOriginX(final double x) {
		if(MathUtils.INST.isValidCoord(x)) {
			originx = x;
		}
	}


	@Override
	public void setOriginY(final double y) {
		if(MathUtils.INST.isValidCoord(y)) {
			originy = y;
		}
	}


	@Override
	public void copy(final IShape s) {
		super.copy(s);

		if(s instanceof IStdGridProp) {
			final IStdGridProp grid = (IStdGridProp) s;
			gridEndx = grid.getGridEndX();
			gridEndy = grid.getGridEndY();
			gridStartx = grid.getGridStartX();
			gridStarty = grid.getGridStartY();
			originx = grid.getOriginX();
			originy = grid.getOriginY();
			setLabelsSize(grid.getLabelsSize());
		}
	}


	@Override
	public IPoint getGridStart() {
		return ShapeFactory.INST.createPoint(gridStartx, gridStarty);
	}


	@Override
	public IPoint getGridEnd() {
		return ShapeFactory.INST.createPoint(gridEndx, gridEndy);
	}
}