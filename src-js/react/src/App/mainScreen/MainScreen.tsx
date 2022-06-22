import MainSection from "./mainSection/MainSection";
import styles from "./Main.module.css";

function MainScreen() {
  return (
    <div className={styles.mainContainer}>
      <MainSection />
    </div>
  );
}

export default MainScreen;
