# **Dynamic Swing Designer**


:white_check_mark: Compulsory - all bullets

:white_check_mark: Optional - 2/3 bullets

## [Click here for a demo](https://www.youtube.com/watch?v=9YzFeebTpJ4)


## Tasks regarding Compulsory part :star:

:heavy_check_mark: Create the class _MainFrame_ of type _JFrame_, that will also represent the main class of the application. The frame will contain a _ControlPanel_ in the north and a _DesignPanel_ in the center

 - **src/main/java/gui/MainFrame**

:heavy_check_mark: The _ControlPanel_ will allow the user to specify **any class name of a Swing component** (such as javax.swing.JButton, javax.swing.JLabel, etc.), a default text for that component (if applicable) and a button for **creating and adding an instance** of the specified component to the _DesignPanel_

 - **src/main/java/gui/panels/ControlPanel**

:heavy_check_mark: The _DesignPanel_ represents a simple JPanel, using _absolute positioning_ of its components and containing Swing components added by our application

 -  **src/main/java/gui/panels/DesignPanel**

## Tasks regarding Optional part :star: :star:

:heavy_check_mark: The _DesignPanel_ container must be _saved / restored_ in XML format, using [JavaBeans](https://docs.oracle.com/javase/8/docs/api/index.html?java/beans/Introspector.html) serialization. Add the buttons _Save_ and _Load_ to the _ControlPanel_

 - **src/main/java/gui/panels/ControlPanel/save**
 - **src/main/java/gui/panels/ControlPanel/load**

:heavy_check_mark: Whenever the user sets the focus on an added component, its _properties_ should be displayed in a _JTable_ componenet. Use the [java.beans.Introspector](https://docs.oracle.com/javase/8/docs/api/java/beans/Introspector.html) class to get the properties of a Swing component.

 - **src/main/java/gui/panels/PropertiesPanel**
 -  **src/main/java/gui/panels/DesignPanel/addFocusListenerToComponent**
 
:x: The properties of type _String_ and _int_ should be updateable. The user may change the text on a button, the position or size of the components, etc.
 - To do
