package com.jonas.subtitles;

import java.awt.datatransfer.DataFlavor;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

public class FileDropHandler {
	private transient javax.swing.border.Border normalBorder;
	private transient java.awt.dnd.DropTargetListener dropListener;
	private static Boolean supportsDnD;

	private static java.awt.Color defaultBorderColor = new java.awt.Color(0f, 0f, 1f, 0.25f);

	public FileDropHandler(final java.awt.Component c, final Listener listener) {
		this(null, c, javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, defaultBorderColor),
				true, listener);
	}

	public FileDropHandler(final java.awt.Component c, final boolean recursive,
			final Listener listener) {
		this(null, c, javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, defaultBorderColor),
				recursive, listener);
	}

	public FileDropHandler(final java.io.PrintStream out, final java.awt.Component c,
			final Listener listener) {
		this(out, c, javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, defaultBorderColor),
				false, listener);
	}

	public FileDropHandler(final java.io.PrintStream out, final java.awt.Component c,
			final boolean recursive, final Listener listener) {
		this(out, c, javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, defaultBorderColor),
				recursive, listener);
	}

	public FileDropHandler(final java.awt.Component c, final javax.swing.border.Border dragBorder,
			final Listener listener) {
		this(null, c, dragBorder, false, listener);
	}

	public FileDropHandler(final java.awt.Component c, final javax.swing.border.Border dragBorder,
			final boolean recursive, final Listener listener) {
		this(null, c, dragBorder, recursive, listener);
	}

	public FileDropHandler(final java.io.PrintStream out, final java.awt.Component c,
			final javax.swing.border.Border dragBorder, final Listener listener) {
		this(out, c, dragBorder, false, listener);
	}

	public FileDropHandler(final java.io.PrintStream out, final java.awt.Component c,
			final javax.swing.border.Border dragBorder, final boolean recursive,
			final Listener listener) {

		if (supportsDnD()) {
			dropListener = new java.awt.dnd.DropTargetListener() {
				public void dragEnter(java.awt.dnd.DropTargetDragEvent evt) {
					log(out, "FileDropHandler: dragEnter event.");
					if (isDragOk(out, evt)) {
						if (c instanceof javax.swing.JComponent) {
							javax.swing.JComponent jc = (javax.swing.JComponent) c;
							normalBorder = jc.getBorder();
							log(out, "FileDropHandler: normal border saved.");
							jc.setBorder(dragBorder);
							log(out, "FileDropHandler: drag border set.");
						}
						evt.acceptDrag(java.awt.dnd.DnDConstants.ACTION_COPY);
						log(out, "FileDropHandler: event accepted.");
					} else {
						evt.rejectDrag();
						log(out, "FileDropHandler: event rejected.");
					}
				}

				public void dragOver(java.awt.dnd.DropTargetDragEvent evt) {
				}

				@SuppressWarnings("unchecked")
				public void drop(java.awt.dnd.DropTargetDropEvent evt) {
					log(out, "FileDropHandler: drop event.");
					try {
						java.awt.datatransfer.Transferable tr = evt.getTransferable();
						if (tr.isDataFlavorSupported(
								java.awt.datatransfer.DataFlavor.javaFileListFlavor)) {
							evt.acceptDrop(java.awt.dnd.DnDConstants.ACTION_COPY);
							log(out, "FileDropHandler: file list accepted.");
							@SuppressWarnings("rawtypes")
							java.util.List fileList = (java.util.List) tr.getTransferData(
									java.awt.datatransfer.DataFlavor.javaFileListFlavor);
							@SuppressWarnings({"rawtypes", "unused"})
							java.util.Iterator iterator = fileList.iterator();
							java.io.File[] filesTemp = new java.io.File[fileList.size()];
							fileList.toArray(filesTemp);
							final java.io.File[] files = filesTemp;
							if (listener != null)
								listener.filesDropped(files);
							evt.getDropTargetContext().dropComplete(true);
							log(out, "FileDropHandler: drop complete.");
						} else {
							DataFlavor[] flavors = tr.getTransferDataFlavors();
							boolean handled = false;
							for (int zz = 0; zz < flavors.length; zz++) {
								if (flavors[zz].isRepresentationClassReader()) {
									evt.acceptDrop(java.awt.dnd.DnDConstants.ACTION_COPY);
									log(out, "FileDropHandler: reader accepted.");

									Reader reader = flavors[zz].getReaderForText(tr);

									BufferedReader br = new BufferedReader(reader);

									if (listener != null)
										listener.filesDropped(createFileArray(br, out));
									evt.getDropTargetContext().dropComplete(true);
									log(out, "FileDropHandler: drop complete.");
									handled = true;
									break;
								}
							}
							if (!handled) {
								log(out, "FileDropHandler: not a file list or reader - abort.");
								evt.rejectDrop();
							}
						}
					} catch (java.io.IOException io) {
						log(out, "FileDropHandler: IOException - abort:");
						io.printStackTrace(out);
						evt.rejectDrop();
					} catch (java.awt.datatransfer.UnsupportedFlavorException ufe) {
						log(out, "FileDropHandler: UnsupportedFlavorException - abort:");
						ufe.printStackTrace(out);
						evt.rejectDrop();
					} finally {
						if (c instanceof javax.swing.JComponent) {
							javax.swing.JComponent jc = (javax.swing.JComponent) c;
							jc.setBorder(normalBorder);
							log(out, "FileDropHandler: normal border restored.");
						}
					}
				}

				public void dragExit(java.awt.dnd.DropTargetEvent evt) {
					log(out, "FileDropHandler: dragExit event.");
					if (c instanceof javax.swing.JComponent) {
						javax.swing.JComponent jc = (javax.swing.JComponent) c;
						jc.setBorder(normalBorder);
						log(out, "FileDropHandler: normal border restored.");
					}
				}
				public void dropActionChanged(java.awt.dnd.DropTargetDragEvent evt) {
					log(out, "FileDropHandler: dropActionChanged event.");
					if (isDragOk(out, evt)) {
						evt.acceptDrag(java.awt.dnd.DnDConstants.ACTION_COPY);
						log(out, "FileDropHandler: event accepted.");
					} else {
						evt.rejectDrag();
						log(out, "FileDropHandler: event rejected.");
					}
				}
			};
			makeDropTarget(out, c, recursive);
		} else {
			log(out, "FileDropHandler: Drag and drop is not supported with this JVM");
		}
	}

	private static boolean supportsDnD() { // Static Boolean
		if (supportsDnD == null) {
			boolean support = false;
			try {
				@SuppressWarnings({"unused", "rawtypes"})
				Class arbitraryDndClass = Class.forName("java.awt.dnd.DnDConstants");
				support = true;
			} catch (Exception e) {
				support = false;
			}
			supportsDnD = new Boolean(support);
		}
		return supportsDnD.booleanValue();
	}

	private static String ZERO_CHAR_STRING = "" + (char) 0;
	@SuppressWarnings({"rawtypes", "unchecked"})
	private static File[] createFileArray(BufferedReader bReader, PrintStream out) {
		try {
			java.util.List list = new java.util.ArrayList();
			java.lang.String line = null;
			while ((line = bReader.readLine()) != null) {
				try {
					if (ZERO_CHAR_STRING.equals(line))
						continue;

					java.io.File file = new java.io.File(new java.net.URI(line));
					list.add(file);
				} catch (Exception ex) {
					log(out, "Error with " + line + ": " + ex.getMessage());
				}
			}

			return (java.io.File[]) list.toArray(new File[list.size()]);
		} catch (IOException ex) {
			log(out, "FileDropHandler: IOException");
		}
		return new File[0];
	}

	private void makeDropTarget(final java.io.PrintStream out, final java.awt.Component c,
			boolean recursive) {
		final java.awt.dnd.DropTarget dt = new java.awt.dnd.DropTarget();
		try {
			dt.addDropTargetListener(dropListener);
		} catch (java.util.TooManyListenersException e) {
			e.printStackTrace();
			log(out, "FileDropHandler: Drop will not work due to previous error. Do you have another listener attached?");
		}
		c.addHierarchyListener(new java.awt.event.HierarchyListener() {
			public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
				log(out, "FileDropHandler: Hierarchy changed.");
				java.awt.Component parent = c.getParent();
				if (parent == null) {
					c.setDropTarget(null);
					log(out, "FileDropHandler: Drop target cleared from component.");
				} else {
					new java.awt.dnd.DropTarget(c, dropListener);
					log(out, "FileDropHandler: Drop target added to component.");
				}
			}
		});
		if (c.getParent() != null)
			new java.awt.dnd.DropTarget(c, dropListener);

		if (recursive && (c instanceof java.awt.Container)) {
			java.awt.Container cont = (java.awt.Container) c;
			java.awt.Component[] comps = cont.getComponents();
			for (int i = 0; i < comps.length; i++)
				makeDropTarget(out, comps[i], recursive);
		}
	}

	private boolean isDragOk(final java.io.PrintStream out,
			final java.awt.dnd.DropTargetDragEvent evt) {
		boolean ok = false;
		java.awt.datatransfer.DataFlavor[] flavors = evt.getCurrentDataFlavors();
		int i = 0;
		while (!ok && i < flavors.length) {
			final DataFlavor curFlavor = flavors[i];
			if (curFlavor.equals(java.awt.datatransfer.DataFlavor.javaFileListFlavor)
					|| curFlavor.isRepresentationClassReader()) {
				ok = true;
			}
			i++;
		}

		if (out != null) {
			if (flavors.length == 0)
				log(out, "FileDropHandler: no data flavors.");
			for (i = 0; i < flavors.length; i++)
				log(out, flavors[i].toString());
		}
		return ok;
	}

	private static void log(java.io.PrintStream out, String message) {
		if (out != null)
			out.println(message);
	}

	public static boolean remove(java.awt.Component c) {
		return remove(null, c, true);
	}

	public static boolean remove(java.io.PrintStream out, java.awt.Component c, boolean recursive) {
		if (supportsDnD()) {
			log(out, "FileDropHandler: Removing drag-and-drop hooks.");
			c.setDropTarget(null);
			if (recursive && (c instanceof java.awt.Container)) {
				java.awt.Component[] comps = ((java.awt.Container) c).getComponents();
				for (int i = 0; i < comps.length; i++)
					remove(out, comps[i], recursive);
				return true;
			} else
				return false;
		} else
			return false;
	}

	public static interface Listener {
		public abstract void filesDropped(java.io.File[] files);
	}

	@SuppressWarnings("serial")
	public static class Event extends java.util.EventObject {

		private java.io.File[] files;
		public Event(java.io.File[] files, Object source) {
			super(source);
			this.files = files;
		}

		public java.io.File[] getFiles() {
			return files;
		}

	}

	public static class TransferableObject implements java.awt.datatransfer.Transferable {
		public final static String MIME_TYPE = "application/x-net.iharder.dnd.TransferableObject";
		public final static java.awt.datatransfer.DataFlavor DATA_FLAVOR = new java.awt.datatransfer.DataFlavor(
				FileDropHandler.TransferableObject.class, MIME_TYPE);

		private Fetcher fetcher;
		private Object data;
		private java.awt.datatransfer.DataFlavor customFlavor;
		public TransferableObject(Object data) {
			this.data = data;
			this.customFlavor = new java.awt.datatransfer.DataFlavor(data.getClass(), MIME_TYPE);
		}

		public TransferableObject(Fetcher fetcher) {
			this.fetcher = fetcher;
		}

		@SuppressWarnings("rawtypes")
		public TransferableObject(Class dataClass, Fetcher fetcher) {
			this.fetcher = fetcher;
			this.customFlavor = new java.awt.datatransfer.DataFlavor(dataClass, MIME_TYPE);
		}
		public java.awt.datatransfer.DataFlavor getCustomDataFlavor() {
			return customFlavor;
		}

		public java.awt.datatransfer.DataFlavor[] getTransferDataFlavors() {
			if (customFlavor != null)
				return new java.awt.datatransfer.DataFlavor[]{customFlavor, DATA_FLAVOR,
						java.awt.datatransfer.DataFlavor.stringFlavor};
			else
				return new java.awt.datatransfer.DataFlavor[]{DATA_FLAVOR,
						java.awt.datatransfer.DataFlavor.stringFlavor};
		}

		public Object getTransferData(java.awt.datatransfer.DataFlavor flavor)
				throws java.awt.datatransfer.UnsupportedFlavorException, java.io.IOException {

			if (flavor.equals(DATA_FLAVOR))
				return fetcher == null ? data : fetcher.getObject();

			if (flavor.equals(java.awt.datatransfer.DataFlavor.stringFlavor))
				return fetcher == null ? data.toString() : fetcher.getObject().toString();
			throw new java.awt.datatransfer.UnsupportedFlavorException(flavor);
		}

		public boolean isDataFlavorSupported(java.awt.datatransfer.DataFlavor flavor) {
			if (flavor.equals(DATA_FLAVOR))
				return true;

			if (flavor.equals(java.awt.datatransfer.DataFlavor.stringFlavor))
				return true;

			return false;
		}

		public static interface Fetcher {
			public abstract Object getObject();
		}
	}
}
