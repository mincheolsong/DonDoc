import styles from "./MemberUnit.module.css";
import character from "../../../../../assets/characterImg/0.png"

interface Props {
  userId: number
}

function MemberUnit(props: Props) {

  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <img src={character} alt="" />
        <p>{props.userId}</p>
      </div>
    </div>
  );
}

export default MemberUnit;
