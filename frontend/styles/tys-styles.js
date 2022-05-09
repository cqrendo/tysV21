const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `<dom-module id="dynamic-grid-tys" theme-for="vaadin-grid-pro"> 
  <template> 
   <style>
      :host {
        font-size: var(--lumo-font-size-xs);
      }

	th {	
	  background: #a4a5dd;
	}
      [part="content"] {
        width: 100%;
        box-sizing: border-box;
        flex: 1 1 auto;
        display: flex;
        flex-direction: column;
        padding: 10px !important;
      }

      :host([theme="left"]) {
        align-items: flex-start;
      }

      :host([theme\$="right"]) {
        align-items: flex-end;
      }

      :host([theme]) [part="overlay"] {
        max-width: 65em;
        width: 100%;
      }

      :host([theme="left"]) [part="overlay"],
      :host([theme="right"]) [part="overlay"] {
        flex: 1;
        max-width: 45em;
      }

      :host([theme="top-right"]) [part="overlay"] {
        position: absolute;
        top: var(--lumo-space-xs);
      }

      @media (max-width: 600px), (max-height: 600px) {
        :host([theme]) {
          top: 0;
          left: 0;
          right: 0;
          bottom: 0;
          padding: 0;
        }

        :host([theme]) [part="overlay"] {
          flex: 1;
          width: 100%;
          border-radius: 0 !important;
        }
      }

      /* we need explicitly set height for wrappers inside dialog-flow */
      [part="content"] ::slotted(flow-component-renderer) {
        height: 100%;
      }
    </style> 
  </template> 
 </dom-module><dom-module id="bakery-text-field-theme" theme-for="vaadin-text-field"> 
  <template> 
   <style>
      :host([status="new"]) [part~="input-field"],
      :host([status="confirmed"]) [part~="input-field"] {
        color: var(--lumo-primary-color);
        background: var(--lumo-primary-color-10pct);
      }

      :host([status="ready"]) [part~="input-field"],
      :host([status="delivered"]) [part~="input-field"] {
        color: var(--lumo-success-color);
        background: var(--lumo-success-color-10pct);
      }

      :host([status="problem"]) [part~="input-field"],
      :host([status="cancelled"]) [part~="input-field"] {
        color: var(--lumo-error-color);
        background: var(--lumo-error-color-10pct);
      }

      :host([theme="white"]) [part~="input-field"] {
        color: var(--lumo-secondary-text-color);
        background-color: var(--lumo-base-color);
      }
    </style> 
  </template> 
 </dom-module><dom-module id="bakery-grid-theme" theme-for="vaadin-grid"> 
  <template> 
   <style>
      :host {
        width: 100%;
        margin: auto;
      }

      [part~="row"]:last-child [part~="header-cell"],
      [part~="header-cell"]:not(:empty):not([details-cell]) {
        padding-top: var(--lumo-space-l);
        padding-bottom: var(--lumo-space-m);

        font-size: var(--lumo-font-size-s);
        border-bottom: 1px solid var(--lumo-shade-5pct);
      }

      :host(:not([theme~="no-row-borders"])) [part~="cell"]:not([part~="details-cell"]) {
        border-top: 1px solid var(--lumo-shade-5pct);
      }

      /* a special grid theme for the bakery storefront view */
      :host([theme~="orders"]) {
        background: transparent;
      }

      :host([theme~="orders"]) [part~="cell"]:not(:empty):not([details-cell]) {
        padding: 0;
      }

      :host([theme~="orders"]) [part~="row"][selected] [part~="cell"] {
        background: transparent !important;
      }

      :host([theme~="orders"]) [part~="body-cell"] {
        background: transparent;
      }

      @media (max-width: 600px) {
        :host([theme~="orders"]) [part~="cell"] ::slotted(vaadin-grid-cell-content) {
          padding: 0 !important;
        }
      }

      :host([theme~="dashboard"]) [part~="cell"] ::slotted(vaadin-grid-cell-content) {
        padding: 0;
      }

      :host([theme~="crud"]) {
        max-width: calc(964px + var(--lumo-space-m));
        background-color: var(--lumo-base-color);
      }
    </style> 
  </template> 
 </dom-module>
 
 <dom-module id="shared-styles"> 
  <template> 
   <style>
      *,
      *::before,
      *::after,
      ::slotted(*) {
        box-sizing: border-box;
      }

      :host([hidden]),
      [hidden] {
        display: none !important;
      }

      h2,
      h3 {
        margin-top: var(--lumo-space-m);
        margin-bottom: var(--lumo-space-s);
      }

      h2 {
        font-size: var(--lumo-font-size-xxl);
      }

      h3 {
        font-size: var(--lumo-font-size-xl);
      }

      .scrollable {
        padding: var(--lumo-space-m);
        overflow: auto;
        -webkit-overflow-scrolling: touch;
      }

      .count {
        display: inline-block;
        background: var(--lumo-shade-10pct);
        border-radius: var(--lumo-border-radius);
        font-size: var(--lumo-font-size-s);
        padding: 0 var(--lumo-space-s);
        text-align: center;
      }

      .total {
        padding: 0 var(--lumo-space-s);
        font-size: var(--lumo-font-size-l);
        font-weight: bold;
        white-space: nowrap;
      }

      @media (min-width: 600px) {
        .total {
          min-width: 0;
          order: 0;
          padding: 0 var(--lumo-space-l);
        }
      }

      @media (max-width: 600px) {
        search-bar {
          order: 1;
        }
      }

      .flex {
        display: flex;
      }

      .flex1 {
        flex: 1 1 auto;
      }

      .bold {
        font-weight: 600;
      }

      flow-component-renderer[theme="dialog"],
      flow-component-renderer[theme="dialog"] > div {
        display: flex;
        flex-direction: column;
        flex: auto;
      }
    </style> 
  </template> 
 </dom-module><custom-style> 
  <style>
    @keyframes v-progress-start {
      0% {
        width: 0%;
      }
      100% {
        width: 50%;
      }
    }

    .v-loading-indicator,
    .v-system-error,
    .v-reconnect-dialog {
    	position: absolute;
    	left: 0;
    	top: 0;
    	border: none;
    	z-index: 10000;
    	pointer-events: none;
    }

    .v-system-error,
    .v-reconnect-dialog {
    	display: flex;
    	right: 0;
    	bottom: 0;
    	background: var(--lumo-shade-40pct);
    	flex-direction: column;
      align-items: center;
      justify-content: center;
      align-content: center;
    }

    .v-system-error .caption,
    .v-system-error .message,
    .v-reconnect-dialog .text {
    	width: 30em;
    	max-width: 100%;
    	padding: var(--lumo-space-xl);
    	background: var(--lumo-base-color);
    	border-radius: 4px;
    	text-align: center;
    }

    .v-system-error .caption {
    	padding-bottom: var(--lumo-space-s);
    	border-bottom-left-radius: 0;
    	border-bottom-right-radius: 0;
    }

    .v-system-error .message {
    	pointer-events: all;
    	padding-top: var(--lumo-space-s);
    	border-top-left-radius: 0;
    	border-top-right-radius: 0;
    	color: grey;
    }

    .v-loading-indicator {
    	position: fixed !important;
    	width: 50%;
    	opacity: 0.6;
    	height: 4px;
    	background: var(--lumo-primary-color);
    	transition: none;
    	animation: v-progress-start 1000ms 200ms both;
    }

    .v-loading-indicator[style*="none"] {
    	display: block !important;
    	width: 100% !important;
    	opacity: 0;
    	transition: opacity 500ms 300ms, width 300ms;
    	animation: none;
    }
  </style> 
 </custom-style>`;

document.head.appendChild($_documentContainer.content);


