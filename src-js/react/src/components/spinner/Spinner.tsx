import React from 'react'
import styles from "./Spinner.module.css";
import { IoEarthOutline } from "react-icons/io5";

export default function Spinner() {
  return (
    <div className={styles.load_animation}>
        <IoEarthOutline className={styles.animation} />
      </div>
  )
}
