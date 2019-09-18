package com.example.networkt02.beans;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by SongZhihao on 2019/9/10.
 */
public class TrafficLightBean implements Comparable<TrafficLightBean> {

	public static int select = 0;

	private int id;
	private int green_light;
	private int red_light;
	private int yellow_light;
	private List<?> ids;


	public static int getSelect() {
		return select;
	}

	public static void setSelect(int select) {
		TrafficLightBean.select = select;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGreen_light() {
		return green_light;
	}

	public void setGreen_light(int green_light) {
		this.green_light = green_light;
	}

	public int getRed_light() {
		return red_light;
	}

	public void setRed_light(int red_light) {
		this.red_light = red_light;
	}

	public int getYellow_light() {
		return yellow_light;
	}

	public void setYellow_light(int yellow_light) {
		this.yellow_light = yellow_light;
	}

	@Override
	public String toString() {
		return "TrafficLightBean{" +
				"id=" + id +
				", green_light=" + green_light +
				", red_light=" + red_light +
				", yellow_light=" + yellow_light +
				", ids=" + ids +
				'}';
	}

	/**
	 * 实现排序规则
	 * @param o
	 * @return
	 */

	@Override
	public int compareTo(TrafficLightBean o) {
		switch (select){
			case 0:
				return this.id-o.id;
			case 1:
				return o.id-this.id;
			case 2:
				return this.red_light-o.red_light;
			case 3:
				return o.red_light-this.red_light;
			case 4:
				return this.green_light-o.green_light;
			case 5:
				return o.green_light-this.green_light;
			case 6:
				return this.yellow_light-o.yellow_light;
			case 7:
				return o.yellow_light-this.yellow_light;

		}
		return 0;
	}
}
