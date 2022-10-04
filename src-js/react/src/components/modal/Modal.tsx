import React from "react";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { setModalOpen } from "../../store/reducers/FetchSlice";
import styles from "./Modal.module.css";

export default function Modal({ children }: { children: any }) {
  const { isModalOpen } = useAppSelector((state) => state.fetchReducer);
  const dispatch = useAppDispatch();
  return (
    <div
      className={isModalOpen ? styles.modalActive : styles.modal}
      onClick={() => dispatch(setModalOpen(false))}
    >
      <div
        className={
          isModalOpen ? styles.modalContentActive : styles.modalContent
        }
        onClick={(e) => e.stopPropagation()}
      >
        {children}
      </div>
    </div>
  );
}
