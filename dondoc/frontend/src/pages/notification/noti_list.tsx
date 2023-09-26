import { useState } from "react";
import styles from "./Search.module.css";



function Noti_List() {
  const [NoteList, setNoteList] = useState<[]>([])

  return (
    <>
    <div className={styles.Background}>
      <input className={styles.SearchBar} placeholder="추가하고 싶은 친구의 전화번호를 입력해 주세요."/>
      <div></div>
    </div>
    </>
  );
}

export default Noti_List;
