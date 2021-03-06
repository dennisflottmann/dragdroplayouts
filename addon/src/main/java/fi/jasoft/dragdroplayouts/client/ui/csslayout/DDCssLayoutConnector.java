/*
 * Copyright 2014 John Ahlroos
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package fi.jasoft.dragdroplayouts.client.ui.csslayout;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.csslayout.CssLayoutConnector;
import com.vaadin.shared.ui.Connect;

import fi.jasoft.dragdroplayouts.DDCssLayout;
import fi.jasoft.dragdroplayouts.client.VDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.util.HTML5Support;

@Connect(DDCssLayout.class)
public class DDCssLayoutConnector extends CssLayoutConnector implements Paintable, VHasDragFilter {

  private HTML5Support html5Support;

  @Override
  public VDDCssLayout getWidget() {
    return (VDDCssLayout) super.getWidget();
  }

  @Override
  public DDCssLayoutState getState() {
    return (DDCssLayoutState) super.getState();
  }

  @Override
  protected void init() {
    super.init();
    VDragDropUtil.listenToStateChangeEvents(this, getWidget());
  }

  public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
    VDDCssLayoutDropHandler dropHandler = new VDDCssLayoutDropHandler(this);
    VDragDropUtil.updateDropHandlerFromUIDL(uidl, this, dropHandler);
    if (html5Support != null) {
      html5Support.disable();
    }
    html5Support = HTML5Support.enable(this, dropHandler);
  }

  @Override
  public void onUnregister() {
    if (html5Support != null) {
      html5Support.disable();
      html5Support = null;
    }
    super.onUnregister();
  }

  @Override
  public VDragFilter getDragFilter() {
    return getWidget().getDragFilter();
  }

  @Override
  public void setDragFilter(VDragFilter filter) {
    getWidget().setDragFilter(filter);
  }
}
