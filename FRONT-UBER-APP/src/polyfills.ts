// Fix for "global is not defined" error in Karma tests
(window as any).global = window;
