package rental.cosplay.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

public class ComboBox<E> extends JComboBox<E> {

    public String getLabeText() {
        return labeText;
    }

    public void setLabeText(String labeText) {
        this.labeText = labeText;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    private String labeText;
    private Color lineColor = new Color(80, 141, 78);
    private boolean mouseOver;

    @Override
    public void updateUI() {
        super.updateUI();
        installUI();
    }

    private void installUI() {
        setUI(new ComboUI(this));
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> jlist, Object o, int i, boolean bln, boolean bln1) {
                Component com = super.getListCellRendererComponent(jlist, o, i, bln, bln1);
                setBorder(new EmptyBorder(5, 5, 5, 5));
                if (bln) {
                    com.setBackground(new Color(240, 240, 240));
                }
                return com;
            }
        });
    }
    
    public ComboBox(String labelText) {
        this.labeText = labelText;
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 3, 5, 3));
        installUI();
    }

    private class ComboUI extends BasicComboBoxUI {

        // Simple replacement for org.jdesktop.animation.timing.Animator
        private final SimpleAnimator animator;
        private boolean animateHinText = true;
        private float location;
        private boolean show;
        private ComboBox combo;

        public ComboUI(ComboBox combo) {
            this.combo = combo;
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent me) {
                    mouseOver = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    mouseOver = false;
                    repaint();
                }
            });
            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent fe) {
                    showing(false);
                }

                @Override
                public void focusLost(FocusEvent fe) {
                    showing(true);
                }
            });
            addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent ie) {
                    if (!isFocusOwner()) {
                        if (getSelectedIndex() == -1) {
                            showing(true);
                        } else {
                            showing(false);
                        }
                    }
                }
            });
            addPopupMenuListener(new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
                    if (arrowButton != null) {
                        arrowButton.setBackground(new Color(200, 200, 200));
                    }
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {
                    if (arrowButton != null) {
                        arrowButton.setBackground(new Color(150, 150, 150));
                    }
                }

                @Override
                public void popupMenuCanceled(PopupMenuEvent pme) {
                    if (arrowButton != null) {
                        arrowButton.setBackground(new Color(150, 150, 150));
                    }
                }
            });

            SimpleTimingTarget target = new SimpleTimingTargetAdapter() {
                @Override
                public void begin() {
                    animateHinText = getSelectedIndex() == -1;
                }

                @Override
                public void timingEvent(float fraction) {
                    location = fraction;
                    repaint();
                }
            };
            animator = new SimpleAnimator(300, target);
            animator.setResolution(15);
            animator.setEasing(SimpleAnimator.EASING_EASE_IN_OUT);
        }

        @Override
        public void paintCurrentValueBackground(Graphics grphcs, Rectangle rctngl, boolean bln) {

        }

        @Override
        protected JButton createArrowButton() {
            return new ArrowButton();
        }

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup pop = new BasicComboPopup(comboBox) {
                @Override
                protected JScrollPane createScroller() {
                    list.setFixedCellHeight(30);
                    JScrollPane scroll = new JScrollPane(list);
                    scroll.setBackground(Color.WHITE);
                    ScrollBarCustom sb = new ScrollBarCustom();
                    sb.setUnitIncrement(30);
                    sb.setForeground(new Color(180, 180, 180));
                    scroll.setVerticalScrollBar(sb);
                    
                    return scroll;
                }
            };
            pop.setBorder(new LineBorder(new Color(200, 200, 200), 1));
            return pop;
        }

        @Override
        public void paint(Graphics grphcs, JComponent jc) {
            super.paint(grphcs, jc);
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            int width = getWidth();
            int height = getHeight();
            if (mouseOver) {
                g2.setColor(lineColor);
            } else {
                g2.setColor(new Color(150, 150, 150));
            }
            g2.fillRect(2, height - 1, width - 4, 1);
            createHintText(g2);
            createLineStyle(g2);
            g2.dispose();
        }

        private void createHintText(Graphics2D g2) {
            Insets in = getInsets();
            g2.setColor(new Color(150, 150, 150));
            FontMetrics ft = g2.getFontMetrics();
            Rectangle2D r2 = ft.getStringBounds(combo.getLabeText(), g2);
            double height = getHeight() - in.top - in.bottom;
            double textY = (height - r2.getHeight()) / 2;
            double size;
            if (animateHinText) {
                if (show) {
                    size = 18 * (1 - location);
                } else {
                    size = 18 * location;
                }
            } else {
                size = 18;
            }
            g2.drawString(combo.getLabeText(), in.right, (int) (in.top + textY + ft.getAscent() - size));
        }

        private void createLineStyle(Graphics2D g2) {
            if (isFocusOwner()) {
                double width = getWidth() - 4;
                int height = getHeight();
                g2.setColor(lineColor);
                double size;
                if (show) {
                    size = width * (1 - location);
                } else {
                    size = width * location;
                }
                double x = (width - size) / 2;
                g2.fillRect((int) (x + 2), height - 2, (int) size, 2);
            }
        }

        private void showing(boolean action) {
            if (animator.isRunning()) {
                animator.stop();
            } else {
                location = 1;
            }
            animator.setStartFraction(1f - location);
            show = action;
            location = 1f - location;
            animator.start();
        }

        private class ArrowButton extends JButton {

            public ArrowButton() {
                setContentAreaFilled(false);
                setBorder(new EmptyBorder(5, 5, 5, 5));
                setBackground(new Color(150, 150, 150));
            }

            @Override
            public void paint(Graphics grphcs) {
                super.paint(grphcs);
                Graphics2D g2 = (Graphics2D) grphcs;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width = getWidth();
                int height = getHeight();
                int size = 10;
                int x = (width - size) / 2;
                int y = (height - size) / 2 + 5;
                int px[] = {x, x + size, x + size / 2};
                int py[] = {y, y, y + size};
                g2.setColor(getBackground());
                g2.fillPolygon(px, py, px.length);
                g2.dispose();
            }
        }

        // --- Simple animation helpers ---
        private interface SimpleTimingTarget {
            default void begin() {}
            void timingEvent(float fraction);
            default void end() {}
        }

        private static class SimpleTimingTargetAdapter implements SimpleTimingTarget {
            @Override
            public void timingEvent(float fraction) { /* no-op */ }
        }

        private static class SimpleAnimator {
            static final int EASING_LINEAR = 0;
            static final int EASING_EASE_IN_OUT = 1;

            private final int durationMs;
            private final SimpleTimingTarget target;
            private Timer timer;
            private long startTimeMs;
            private float startFraction = 0f;
            private boolean running = false;
            private int resolutionMs = 15; // default ~60 FPS
            private int easingMode = EASING_LINEAR;

            SimpleAnimator(int durationMs, SimpleTimingTarget target) {
                this.durationMs = Math.max(1, durationMs);
                this.target = target;
            }

            void setResolution(int resolutionMs) {
                this.resolutionMs = Math.max(1, resolutionMs);
            }

            void setEasing(int mode) {
                this.easingMode = mode;
            }

            void setStartFraction(float f) {
                this.startFraction = clamp(f);
            }

            boolean isRunning() { return running; }

            void start() {
                stop();
                running = true;
                target.begin();
                long now = System.currentTimeMillis();
                long offset = (long) (clamp(startFraction) * durationMs);
                startTimeMs = now - offset;
                timer = new Timer(resolutionMs, e -> {
                    float raw = (System.currentTimeMillis() - startTimeMs) / (float) durationMs;
                    float frac = clamp(raw);
                    frac = applyEasing(frac);
                    target.timingEvent(frac);
                    if (raw >= 1f) {
                        ((Timer) e.getSource()).stop();
                        running = false;
                        target.end();
                    }
                });
                timer.setRepeats(true);
                timer.start();
            }

            void stop() {
                if (timer != null) {
                    timer.stop();
                    timer = null;
                }
                running = false;
            }

            private static float clamp(float v) { return v < 0f ? 0f : (v > 1f ? 1f : v); }

            private float applyEasing(float t) {
                if (easingMode == EASING_EASE_IN_OUT) {
                    // Cosine ease-in-out
                    return (float) (0.5 * (1 - Math.cos(Math.PI * t)));
                }
                return t; // linear
            }
        }
    }
}
