/** 
 * FractalBox.java:
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
*
*FractalBox class.
*@author Jason cusolito
*/

public class FractalBox extends Rectangle2D.Float 
{
    //---------------- class variables ------------------------------
    
    //---------------- instance variables ------------------------------
    private FractalBox[]   _children;
    private Rectangle2D.Float   _spawn;
    private int            _myDepth;
    private double width, height , osset;
    private Color opaqueColor;

    //------------------------ constructors ----------------------------
    /**
     *   constructor must recursively create children.
     *  @param depth int
     */
    public FractalBox( int depth )
    {
        
        _myDepth = depth;
        
        //***************************************************************
        //  Need to create the 4 children unless depth has reached the 
        //    max specified in the FractalSpec class
        //  
        //***************************************************************
        if( _myDepth < FractalSpecs.maxDepth )
        {
            
            _children = new FractalBox[ 4 ];
            
            for( int x = 0; x < 4 ; x++ )
            {
                _children[ x ] = new FractalBox( _myDepth + 1 );
            }
            
        }
        
        
        
    }
    //------------------- propagate( ) -----------------------------------
    /** 
     * this method (or one like it) initiates the recursive update of
     * location and sizes of all the FractalShapes beginning with this one.
     * 
     * Don't do it all here, though. Make helper methods to keep the code
     * simpler.
     */
    public void propagate( )
    {
        //**********************************************************
        //   need to compute location/size info for the children recursively
        //**********************************************************
        
        
        if( _myDepth <= 1 )
        {
            width = FractalSpecs.baseWidth;
            height =  width * FractalSpecs.aspectRatio; 
            
            
        }
        //   System.out.println("depth"+FractalSpecs.maxDepth );
        if( _children != null )
        {
            
            this.create1( );
            this.create2( );
            this.create3( );
            this.create4( );

        }
        
        
        
    }

/**
* creates first child tile.
*/    
    
    private void create1( )
    {
        
        FractalBox spawn = _children[ 0 ];
        spawn.width = width * FractalSpecs.childSizeRatio;;
        spawn.height = height * FractalSpecs.childSizeRatio;
        spawn.osset = width * FractalSpecs.childOffset;
        spawn.setRect( x - spawn.osset + ( width - spawn.width ) 
            , y + height , spawn.width , spawn.height );
        spawn.propagate( );
    }
/**
* creates second child tile.
*/    
    
    private void create2( )
    {
        
        FractalBox spawn = _children[ 1 ];
        spawn.width = width * FractalSpecs.childSizeRatio;
        spawn.height = height * FractalSpecs.childSizeRatio;
        spawn.osset = width * FractalSpecs.childOffset;
        spawn.setRect( x - spawn.width , y + spawn.height - spawn.osset , 
            spawn.width , spawn.height );
        spawn.propagate( );
    }
/**
* creates third child tile.
*/    
    
    private void create3( )
    {
        
        FractalBox spawn = _children[ 2 ];
        spawn.width = width * FractalSpecs.childSizeRatio;
        spawn.height = height * FractalSpecs.childSizeRatio;
        spawn.osset = width * FractalSpecs.childOffset;
        spawn.setRect( x - spawn.width , y + spawn.height - spawn.osset ,
             spawn.width , spawn.height );
        spawn.setRect( x + width , y  + spawn.osset ,
             spawn.width , spawn.height );
        spawn.propagate( );
    }
/**
* creates fourth child tile.
*/    
    
    private void create4( )
    {
        
        FractalBox spawn = _children[ 3 ];
        spawn.width = width * FractalSpecs.childSizeRatio;
        spawn.height = height * FractalSpecs.childSizeRatio;
        spawn.osset = width * FractalSpecs.childOffset;
        spawn.setRect( x + spawn.osset , y - spawn.height ,
             spawn.width , spawn.height );
        spawn.propagate( );
    }
    
    //--------------------- display( Graphics2D ) -----------------------
    /**
     * method called by FractalGUI.paintComponent.
     *@param context Graphics2D
     */   
    public void display( Graphics2D context ) 
    {
        
        
        Color saveColor = context.getColor( );
        opaqueColor = new Color ( 0 , 0 , 255 , 40 );


        
        //////////////////////////////////////////////////////////////
        // Actual color needs to be determined by depth, so will proably want
        //   an array of colors
        //////////////////////////////////////////////////////////////
        // To implement the semi-transparent option, once you've chosen
        //   an opaque color based on depth, you can use the color values
        //   from that object to create a new color object with the same
        //    RGB, but with an A (alpha) of 0.5 or lower.
        ///////////////////////////////////////////////////////////////
        
        
        ////////////////////////////////////////////////////////////////
        // Need to recursively draw all children
        //
        // Each depth should have a different color.
        ///////////////////////////////////////////////////////////////
        if( FractalSpecs.fill == true )
        {
            context.setColor( Color.BLUE );
            context.fill( this );

        }
        
        

        if ( FractalSpecs.fill != true )
        {
            context.setColor( Color.BLUE );    
            context.draw( this );

        }
        
        if ( FractalSpecs.opaqueFill == true )
        {
            context.setColor( opaqueColor );
            context.fill( this );
        }
        
        
        if( _children != null )
        {
            
            for( FractalBox littleBox : _children )
               
                littleBox.display( context );
        }
        
        context.setColor( saveColor );
    }
}