package com.catalystitservices.vaadin;

import com.vaadin.Application;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Vaadin 6, check fields when they may have changed value by using an onBlur
 * listener
 */
@SuppressWarnings( "serial" )
public class TestValueChange extends Application {
	private IndexedContainer container;
	private Table table;
	private int itemId = 0;

	/**
	 * This starts the webapp by setting the main window
	 */
	@Override
	public void init() {
		System.out.println("in init()");
		setMainWindow( new TestWindow() );
	}

	/**
	 * Setup the main window: add table and button
	 */
	public class TestWindow extends Window {
		public TestWindow() {
			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.addComponent( createTable() );
			setContent( mainLayout );
		}

		/**
		 * Create a table and container
		 * 
		 * @return the created table object
		 */
		private Table createTable() {
			createContainer();
			table = new Table();
			table.setContainerDataSource( container );
			table.setTableFieldFactory( new TableFieldFactory() );
			table.setVisibleColumns( new Object[] { "id", "name", "number" } );
			table.setWidth( "40%" );
			table.setColumnExpandRatio( "id", 1 );
			table.setColumnExpandRatio( "name", 5 );
			table.setColumnExpandRatio( "number", 5 );
			table.setPageLength( 7 );
			table.setEditable( true );
			table.setImmediate( true );

			return table;
		}

		/**
		 * Setup the Indexed Container and add items
		 */
		private void createContainer() {

			// Set the properties (columns)
			container = new IndexedContainer();
			container.addContainerProperty( "name", String.class, null );
			container.addContainerProperty( "number", Integer.class, null );
			container.addContainerProperty( "id", String.class, null );

			// Add items (rows)
			addItem( "Bob", 10 );
			addItem( "Harry", 1 );
			addItem( "Margaret", 13 );
			addItem( "Glenda", 22 );
			addItem( "Jessica", 24 );
			addItem( null, 99 );
			addItem( "Knute", null );
		}

		/**
		 * Add an item
		 * 
		 * @param name
		 *            name to set
		 * @param number
		 *            number to set
		 */
		private void addItem( String name, Integer number ) {
			Item item = container.addItem( itemId );
			item.getItemProperty( "name" ).setValue( name );
			item.getItemProperty( "number" ).setValue( number );
			item.getItemProperty( "id" ).setValue( itemId );
			item.getItemProperty( "id" ).setReadOnly( true );
			itemId++;
		}
		
		/**
		 * Default field factory, create field and add onBlur listener
		 */
		private class TableFieldFactory extends DefaultFieldFactory {
			
			@Override
			public Field createField( final Container container, 
					final Object itemId, final Object propertyId, 
					Component uiContext ) {

				TextField field = new TextField();
				field.setImmediate( true );
				
				if ( "name".equals( propertyId ) ) {
					System.out.println("Setting: name");
					field.addListener( new FieldEvents.BlurListener() {
						
						/**
						 * Do what you need to with the field here.  It may
						 * have changed value.
						 */
						@Override
						public void blur( BlurEvent event ) {
							String value = event.getSource().toString();
							Item item = container.getItem( itemId );
							System.out.println( "\nValue: " + value );
							System.out.println( "Property ID: " + propertyId );
							
							if ( "Boris".equals( value ) ) {
								item.getItemProperty( "number" ).setValue( 42 );
							}
						}
					} );
				} else if ( "number".equals( propertyId ) ) {
					field.addListener( new FieldEvents.BlurListener() {

						@Override
						public void blur( BlurEvent event ) {
							String value = event.getSource().toString();
							Item item = container.getItem( itemId );
							System.out.println( "\nValue: " + value );
							System.out.println( "Property ID: " + propertyId );
							
							if ( "0".equals( value ) ) {
								item.getItemProperty( "name" )
										.setValue( "Zero" );
							}
						}
					} );
				}
				
				return field;
			}
		}
	}
}
