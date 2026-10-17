/* Навигация между экранами (стек) + жест back-swipe */
import { vibrate } from './utils.js';

const rootEl = () => document.getElementById('screens-root');

export const Nav = {
  stack: [],

  push(screenFactory) {
    const prev = this.stack[this.stack.length - 1];
    const el = screenFactory();
    el.classList.add('screen', 'enter-from-right');
    rootEl().appendChild(el);
    this.stack.push(el);
    prev && prev.classList.add('anim', 'stack-under');
    requestAnimationFrame(() => {
      el.classList.add('anim');
      el.classList.remove('enter-from-right');
      setTimeout(() => { el.classList.remove('anim'); prev && prev.classList.remove('anim'); }, 320);
    });
    vibrate(6);
  },

  pop() {
    if (this.stack.length <= 1) return;
    const top = this.stack.pop();
    const prev = this.stack[this.stack.length - 1];
    prev && prev.classList.add('anim');
    top.classList.add('anim');
    top.style.transform = 'translateX(100%)';
    setTimeout(() => { top.remove(); }, 320);
    prev && prev.classList.remove('stack-under');
    setTimeout(() => { prev && prev.classList.remove('anim'); }, 320);
    vibrate(4);
  },

  peek() { return this.stack[this.stack.length - 1]; },
  depth() { return this.stack.length; }
};
