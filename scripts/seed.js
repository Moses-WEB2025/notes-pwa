/* ============ Демо-данные как на скриншотах (только при первом запуске) ============ */
import { Store, uid } from './core.js';

const D = (day, h, m) => { const d = new Date(); d.setDate(d.getDate() - day); d.setHours(h, m, 0, 0); return d.getTime(); };

export async function seedDemo() {
  const notes = [
    { id: uid(), title: 'Аудиофайл', content: '', color: null, folderId: null, pinned: false, audio: { id: 'aud_demo', duration: 42, mime: 'audio/webm' }, createdAt: D(0,14,45), updatedAt: D(0,14,45) },
    { id: uid(), title: '14 октября был первый день', content: '14 октября была первая стипендия 52372', color: null, folderId: null, pinned: false, audio: null, createdAt: D(3,18,4), updatedAt: D(3,18,4) },
    { id: uid(), title: 'English', content: 'Упражнение 83.2', color: 'blue', folderId: null, pinned: false, audio: null, createdAt: D(7,10,0), updatedAt: D(7,10,0) },
    { id: uid(), title: 'Идеи программирования', content: 'Игра для всех из МУИТ, где они будут иметь свои аватары и передвигаться по кампусу, выполняя задания', color: null, folderId: null, pinned: false, audio: null, createdAt: D(15,9,30), updatedAt: D(15,9,30) },
    { id: uid(), title: 'I have a really bad headache', content: '', color: null, folderId: null, pinned: false, audio: null, createdAt: D(14,16,20), updatedAt: D(14,16,20) },
    { id: uid(), title: 'Пюре рецепт', content: 'Короче вот на две небольшие порции (лучше сразу умножай на 2-3):\n- Картофель 4 шт\n- Молоко 100 мл\n- Масло 20 г', color: null, folderId: null, pinned: false, audio: null, createdAt: D(16,12,0), updatedAt: D(16,12,0) },
    { id: uid(), title: 'Купить', content: 'Вентилятор\nДок на печатать', color: null, folderId: null, pinned: false, audio: null, createdAt: D(25,10,0), updatedAt: D(25,10,0) },
    { id: uid(), title: 'Списки Класса 2612', content: 'Список грантов/платных:\n1. Абакаров Мусагаджи — Бесплатно\n2. Азатова Азиза — Платно\n3. Бергин Никита — Платно\n4. Ведянин Даниил — Бесплатно\n5. Досумбек Асылхан — Бесплатно\n6. Егембердиев Эдилет — Платно\n7. Журкаев Ануар — Бесплатно', color: null, folderId: null, pinned: false, audio: null, createdAt: D(5,9,15), updatedAt: D(5,9,15) },
  ];
  await Store.seed(notes, []);
}
