package com.PortalAsteroidjava;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;



public class SpaceObject extends Sprite
{
    /**
     * The x-component of this sprite's velocity.
     */

    protected float vx;

    /**
     * The y-component of this sprite's velocity.
     */

    protected float vy;

    /**
     * The state of this sprite.
     */

    protected int state;

    /**
     * The normal state of a sprite.  This constant is defined to be 0.
     */

    public static final int STATE_NORMAL = 0;

    /**
     * The amount of time this sprite has spent in its current state.
     * Measured in seconds.
     */

    private float stateTime;
    

    /**
     * Creates a new sprite at the given position.
     * The sprite will be black and stationary.
     *
     * @param initX the x-coordinate of the new sprite
     * @param initY the y-coordinate of the new sprite
     * @param r the radius of a circle that bounds the new sprite
     */

    public SpaceObject(TextureRegion region, float initX, float initY, float r)
    {
	this(region, initX, initY, r, 0, 0);
    }

    /**
     * Creates a new sprite at the given position.
     *
     * @param initX the x-coordinate of the new sprite
     * @param initY the y-coordinate of the new sprite
     * @param r the radius of a circle that bounds the new sprite
     * @param initVx the x-component of the velocity of the new sprite
     * @param initVy the y-component of the velocity of the new sprite
     * @param c the color of the new sprite
     */

    public SpaceObject(TextureRegion region, float initX, float initY, float r, float initVx, float initVy)
    {
    	super(region);
    	
	setBounds(initX, initY, r, r);
	vx = initVx;
	vy = initVy;
    }

    /**
     * Returns the maximum speed of this sprite.  This implementation
     * returns positive infinity, indicating no limit.  Subclasses should
     * override this method so that <CODE>update</CODE> will take
     * a maximum speed into account.
     */

    public float getMaximumSpeed()
    {
	return 0;
    }

    /**
     * Sets the velocity of this sprite.  If the given velocity exceeds the
     * maximum, it will be reduced so that it equals the maximum.  In such
     * a case the direction will remain the same.
     *
     * @param vx the new velocity in the horizontal direction
     * @param vy the new velocity in the vertical direction
     */

    public void setVelocity(float vxNew, float vyNew)
    {
    	vx = vxNew;
		vy = vyNew;

	// limit speed

		float v = (float)Math.sqrt(vx * vx + vy * vy);
		float maxV = getMaximumSpeed();

		if (v > maxV)
		{
			vx /= v / maxV;
			vy /= v / maxV;
		}
    }
    
    public float getVelocityX()
    {
    	return vx;
    }
    
    public float getVelocityY()
    {
    	return vy;
    }

    /**
     * Returns the polygonal outline of this Sprite.  This implementation
     * returns the square inscribed in the bounding circle as defined
     * by <CODE>getBoundingRadius</CODE> and that has sides parallel to the
     * axes of the coordinate system.  Subclasses should override this
     * method for sprites of other shapes.
     *
     * @return the outline of this sprite
     */

    /*public GeneralPath getOutline()
    {
	GeneralPath outline = new GeneralPath();

	outline.moveTo((float)(x + Math.cos(Math.PI / 4) * getBoundingRadius()),
		       (float)(y + Math.sin(Math.PI / 4) * getBoundingRadius()));
	outline.lineTo((float)(x + Math.cos(Math.PI / 4) * getBoundingRadius()),
		       (float)(y - Math.sin(Math.PI / 4) * getBoundingRadius()));
	outline.lineTo((float)(x - Math.cos(Math.PI / 4) * getBoundingRadius()),
		       (float)(y - Math.sin(Math.PI / 4) * getBoundingRadius()));
	outline.lineTo((float)(x - Math.cos(Math.PI / 4) * getBoundingRadius()),
		       (float)(y + Math.sin(Math.PI / 4) * getBoundingRadius()));
	outline.closePath();

	return outline;
    }*/

	// we also need to test whether points of one polygon are
	// inside the other; we assume here that updates are often
	// enough so that there is an edge collision detected above
	// before such a case (but suppose the peanut randomly appears
	// inside the player or the elephant...)



    /**
     * Returns the list of points on the outline of this sprite.  The
     * starting point is on the list at the start and at the end.
     * This implementation currently returns an <CODE>ArrayList</CODE>
     * in order to facilitate random access to the points.
     *
     * @return a list of points on the outline of this sprite
     */

    /*private List< Point2D > getPointsList()
    {
	List< Point2D > l = new ArrayList< Point2D >();

	float[] coords = new float[6]; // for getting coords from iterator

	PathIterator i = getOutline().getPathIterator(new AffineTransform());
	while (!i.isDone())
	    {
		i.currentSegment(coords);
		l.add(new Point2D.float(coords[0], coords[1]));
		i.next();
	    }

	// add first point as last

	l.add(l.get(0));

	return l;
    }*/

    /**
     * Updates this sprite's position and velocity.  The velocity will be
     * adjusted so that it does not exceed the maximum speed as specified by
     * the <CODE>getMaximumSpeed</CODE> method.
     *
     * @param t the time since the last update, in seconds
     * @param w the world this sprite belongs to
     */

    public void update(float t, GameModel m)
    {
	stateTime += t;
	translate(vx * t, vy * t);
    }

    /**
     * Returns the state of this sprite.
     *
     * @return the current state
     */

    public int getState()
    {
	return state;
    }

    /**
     * Sets the state of this sprite.  The state timer is reset to 0.
     *
     * @param s the new state
     */

    public void setState(int s)
    {
	state = s;
	stateTime = 0;
    }

    /**
     * Returns the amount of time this sprite has spent in its current state.
     *
     * @return the time in the current state
     */

    public float getStateTime()
    {
	return stateTime;
    }
}
